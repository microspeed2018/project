package com.zjzmjr.core.service.handler;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 读取多个配置文件
 * 
 * @author oms
 * @version $Id: MyBatisSessionFactoryBean.java, v 0.1 2017-7-12 上午10:11:54 oms Exp $
 */
public class MyBatisSessionFactoryBean extends SqlSessionFactoryBean {

    private static final Log logger = LogFactory.getLog(MyBatisSessionFactoryBean.class);

    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    private Interceptor[] plugins;

    private Class<?>[] typeAliases;

    private String typeAliasesPackage;

    private TypeHandler<?>[] typeHandlers;

    private String typeHandlersPackage;

    private TransactionFactory transactionFactory;

    private Properties configurationProperties;

    private Resource[] configLocations;

    private DataSource dataSource;

    private String environment = SqlSessionFactoryBean.class.getSimpleName();

    private DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

    private Resource[] mapperLocations;

    private SqlSessionFactory sqlSessionFactory;

    // -settings 是否获取标记--防止多次获取--
    private boolean settings_flag = true;

    // -plugins 是否获取标记--防止多次获取--
    private boolean plugins_flag = true;

    /* 修改该方法 */
    public void setConfigLocation(Resource configLocation) {
        this.configLocations = configLocation != null ? new Resource[] { configLocation } : null;
    }

    /* 增加该方法 */
    public void setConfigLocationX(String filePath) {
        try {
            ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
            // 将加载多个模式匹配的Resource
            Resource[] resources = (Resource[]) resolver.getResources(filePath);
            configLocations = resources;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (logger.isDebugEnabled()) {
            if (configLocations != null) {
                for (Resource resource : configLocations) {
                    logger.debug("===================================" + resource);
                }
            }
        }
    }

    /**
     * 合并mybatis配置文件
     */
    public Document SQLConfigMap() {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        DocumentFactory documentFactory = new DocumentFactory();
        DocumentType docType = documentFactory.createDocType("configuration", "-//mybatis.org//DTD Config 3.0//EN", "http://mybatis.org/dtd/mybatis-3-config.dtd");
        doc.setDocType(docType);
        Element rootElement = doc.addElement("configuration");
        rootElement.addElement("settings");
        rootElement.addElement("typeAliases");
        rootElement.addElement("plugins");
        rootElement.addElement("mappers");
        return doc;
    }

    public void readXML(Resource configXML, final Element elementSettings, final Element elementTypeAlias, final Element elementPlugins, final Element elementMapper) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEntityResolver(new EntityResolver() {

            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                String jarPath = SqlSessionFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String filePath = "org/apache/ibatis/builder/xml/mybatis-3-config.dtd";
                InputStream jarIn = null;
                try {
                    JarFile jarFile = new JarFile(jarPath);
                    JarEntry jarEntry = jarFile.getJarEntry(filePath);
                    jarIn = jarFile.getInputStream(jarEntry);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new InputSource(jarIn);
            }
        });
        /* settings 获取 */
        saxReader.addHandler("/configuration/settings/setting", new ElementHandler() {

            public void onEnd(ElementPath path) {
                if (settings_flag) {
                    // System.out.println("settings 获取---"+path.getPath());
                    Element row = path.getCurrent();
                    Element els = elementSettings.addElement("setting");
                    els.addAttribute("name", row.attributeValue("name")).addAttribute("value", row.attributeValue("value"));
                    row.detach();
                }
            }

            public void onStart(ElementPath arg0) {
            }
        });
        /* typeAliases合并 */
        saxReader.addHandler("/configuration/typeAliases/typeAlias", new ElementHandler() {

            public void onEnd(ElementPath path) {
                Element row = path.getCurrent();
                Element els = elementTypeAlias.addElement("typeAlias");
                els.addAttribute("alias", row.attributeValue("alias")).addAttribute("type", row.attributeValue("type"));
                row.detach();
            }

            public void onStart(ElementPath arg0) {
            }
        });
        /* plugins合并 */
        saxReader.addHandler("/configuration/plugins/plugin", new ElementHandler() {

            public void onEnd(ElementPath path) {
                if (plugins_flag) {
                    // System.out.println("plugins 获取---"+path.getPath());
                    Element row = path.getCurrent();
                    Element els = elementPlugins.addElement("plugin");
                    els.addAttribute("interceptor", row.attributeValue("interceptor"));
                    // child
                    // System.out.println("xxxx=====xxx=="+row.elements().size());
                    if (row.elements() != null) {
                        Iterator iter = row.elementIterator();
                        while (iter.hasNext()) {
                            Element oldels = (Element) iter.next();
                            Element els_property = els.addElement("property");
                            els_property.addAttribute("name", oldels.attributeValue("name")).addAttribute("value", oldels.attributeValue("value"));
                        }

                    }
                    row.detach();
                }
            }

            public void onStart(ElementPath arg0) {
            }
        });
        /* mapper合并 */
        saxReader.addHandler("/configuration/mappers/mapper", new ElementHandler() {

            public void onEnd(ElementPath path) {
                Element row = path.getCurrent();
                Element els = elementMapper.addElement("mapper");
                String mapper = row.attributeValue("mapper");
                String resource = row.attributeValue("resource");
                els.addAttribute("mapper", mapper);
                els.addAttribute("resource", resource);
                row.detach();
            }

            public void onStart(ElementPath arg0) {

            }
        });

        try {
            saxReader.read(configXML.getInputStream());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return SqlSessionFactory
     * @throws IOException
     */
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        Configuration configuration = null;
        XMLConfigBuilder xmlConfigBuilder = null;
        Document document = this.SQLConfigMap();
        Element root = document.getRootElement();
        Element elementSettings = root.element("settings");
        Element elementTypeAlias = root.element("typeAliases");
        Element elementPlugins = root.element("plugins");
        Element elementMapper = root.element("mappers");
        for (Resource configLocation : configLocations) {
            readXML(configLocation, elementSettings, elementTypeAlias, elementPlugins, elementMapper);
            settings_flag = false;
            plugins_flag = false;
        }
        if(!this.plugins_flag){
            root.remove(elementPlugins);
        }
        // Reader reader = null; InputStream inputStream = null;
        if (this.configLocations != null) {
            // --打印合并后的xml配置
            logger.debug("xml=" + document.asXML());
            InputStream inputSteam = new ByteArrayInputStream(document.asXML().getBytes());
            xmlConfigBuilder = new XMLConfigBuilder(inputSteam, null, this.configurationProperties);
            configuration = xmlConfigBuilder.getConfiguration();
            if (inputSteam != null) {
                inputSteam.close();
                inputSteam = null;
            }
            document = null;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Property 'configLocation' not specified,using default MyBatis Configuration");
            }
            configuration = new Configuration();
            configuration.setVariables(this.configurationProperties);
        }

        if (hasLength(this.typeAliasesPackage)) {
            String[] typeAliasPackageArray = tokenizeToStringArray(this.typeAliasesPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeAliasPackageArray) {
                configuration.getTypeAliasRegistry().registerAliases(packageToScan);
                if (logger.isDebugEnabled()) {
                    logger.debug("Scanned package: '" + packageToScan + "' for aliases");
                }
            }
        }
        if (!isEmpty(this.typeAliases)) {
            for (Class<?> typeAlias : this.typeAliases) {
                configuration.getTypeAliasRegistry().registerAlias(typeAlias);
                if (logger.isDebugEnabled()) {
                    logger.debug("Registered type alias: '" + typeAlias + "'");
                }
            }
        }

        if (!isEmpty(this.plugins)) {
            for (Interceptor plugin : this.plugins) {
                configuration.addInterceptor(plugin);
                if (logger.isDebugEnabled()) {
                    logger.debug("Registered plugin: '" + plugin + "'");
                }
            }
        }

        if (hasLength(this.typeHandlersPackage)) {
            String[] typeHandlersPackageArray = tokenizeToStringArray(this.typeHandlersPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeHandlersPackageArray) {
                configuration.getTypeHandlerRegistry().register(packageToScan);
                if (logger.isDebugEnabled()) {
                    logger.debug("Scanned package: '" + packageToScan + "' for type handlers");
                }
            }
        }

        if (!isEmpty(this.typeHandlers)) {
            for (TypeHandler<?> typeHandler : this.typeHandlers) {
                configuration.getTypeHandlerRegistry().register(typeHandler);
                if (logger.isDebugEnabled()) {
                    logger.debug("Registered type handler: '" + typeHandler + "'");
                }
            }
        }

        if (xmlConfigBuilder != null) {
            try {
                xmlConfigBuilder.parse();
                if (logger.isDebugEnabled()) {
                    logger.debug("Parsed configuration file: '" + this.configLocations + "'");
                }
            } catch (Exception ex) {
                throw new NestedIOException("Failed to parse config resource: " + this.configLocations, ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        if (this.transactionFactory == null) {
            this.transactionFactory = new SpringManagedTransactionFactory();
        }

        Environment environment = new Environment(this.environment, this.transactionFactory, this.dataSource);
        configuration.setEnvironment(environment);

        if (this.databaseIdProvider != null) {
            try {
                configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(this.dataSource));
            } catch (SQLException e) {
                throw new NestedIOException("Failed getting a databaseId", e);
            }
        }

        if (!isEmpty(this.mapperLocations)) {
            for (Resource mapperLocation : this.mapperLocations) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, mapperLocation.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
                } finally {
                    ErrorContext.instance().reset();
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("Parsed mapper file: '" + mapperLocation + "'");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Property 'mapperLocations' was not specified or no matching resources found");
            }
        }
        return this.sqlSessionFactoryBuilder.build(configuration);
    }

    public void afterPropertiesSet() throws Exception {
        notNull(dataSource, "Property 'dataSource' is required");
        notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

    /*
     * public Class<? extends SqlSessionFactory> getObjectType() { return
     * this.sqlSessionFactory == null ? SqlSessionFactory.class :
     * this.sqlSessionFactory.getClass(); }
     * 
     * public void onApplicationEvent(ApplicationEvent event) { if (failFast &&
     * event instanceof ContextRefreshedEvent) { // fail-fast -> check all
     * statements are completed
     * this.sqlSessionFactory.getConfiguration().getMappedStatementNames(); } }
     */
    public SqlSessionFactoryBuilder getSqlSessionFactoryBuilder() {
        return sqlSessionFactoryBuilder;
    }

    public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
        this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
    }

    public void setPlugins(Interceptor[] plugins) {
        this.plugins = plugins;
    }

    public void setTypeAliases(Class<?>[] typeAliases) {
        this.typeAliases = typeAliases;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    public void setConfigurationProperties(Properties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
        }
    }
}
