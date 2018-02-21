package com.zjzmjr.finance.web.test;

public class ReadTextFileTest {

//    @SuppressWarnings("resource")
//    @Test
//    public void read(){
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\tools\\apache-tomcat-6.0.32\\webapps\\ROOT\\WEB-INF\\applicationContext.xml");
//        IBankCardFacade bankCardFacade =  (IBankCardFacade)context.getBean("bankCardFacade");
//        IUserFacade userFacade = (IUserFacade)context.getBean("userFacade");
//        List<String> txtContent = readTxtFile("C:\\Users\\oms\\Documents\\Tencent Files\\45084204\\FileRecv\\batch_out_201612151001335578_2.txt");
//        String[] info = null;
//        ResultPage<User> userRst = null;
//        for(String content : txtContent){
//            info = content.split(",");
//            UserQuery query = new UserQuery();
//            query.setIdentityNo(info[3]);
//            query.setName(info[4]);
//            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
//            userRst = userFacade.getUsersInfo(query);
//            if(userRst.isSuccess()){
//                for(User user : userRst.getList()){
//                    if(user.getId().toString().equals(info[1])){
//                        ResultEntry<BankCardInfo> bankRst = bankCardFacade.getBankByUseId(user.getId());
//                        if(bankRst.isSuccess()){
//                            BankCard bankCard = new BankCard();
//                            bankCard.setId(bankRst.getT().getId());
//                            bankCard.setUserId(user.getId());
//                            bankCard.setBankcard(bankRst.getT().getBankcard());
//                            bankCard.setBank(bankRst.getT().getBank());
//                            bankCard.setAgreeNo(info[6]);
//                            bankCardFacade.updateBankCard(bankCard);
//                        }
//                    }
//                }
//            }
//        }
//        
//    }
//    
//    /**
//      * 功能：Java读取txt文件的内容
//      * 步骤：1：先获得文件句柄
//      * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
//      * 3：读取到输入流后，需要读取生成字节流
//      * 4：一行一行的输出。readline()。
//      * 备注：需要考虑的是异常情况
//      * @param filePath
//      */
//    public static List<String> readTxtFile(String filePath) {
//        try {
//            String encoding = "UTF-8";
//            List<String> txtContent = new ArrayList<String>();
//            File file = new File(filePath);
//            if (file.isFile() && file.exists()) { // 判断文件是否存在
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while ((lineTxt = bufferedReader.readLine()) != null) {
//                    txtContent.add(lineTxt);
//                }
//                read.close();
//            } else {
//                System.out.println("找不到指定的文件");
//            }
//            return txtContent;
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//        return null;
//    }
    
}
