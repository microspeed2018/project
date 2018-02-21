/*!
 * ImageTrans
 * Copyright (c) 2010 cloudgamer
 * Blog: http://cloudgamer.cnblogs.com/
 * Date: 2010-8-15
 */

//��������
var ImageTrans = function (container, options) {
    this._initialize(container, options);
    this._initMode();
    if (this._support) {
        this._initContainer();
        this._init();
    } else {//ģʽ��֧��
        this.onError("not support");
    }
};
ImageTrans.prototype = {
    //��ʼ������
    _initialize: function (container, options) {
        var container = this._container = $$(container);
        this._clientWidth = container.clientWidth;//�任������
        this._clientHeight = container.clientHeight;//�任����߶�
        this._img = new Image();//ͼƬ����
        this._style = {};//������ʽ
        this._x = this._y = 1;//ˮƽ/��ֱ�任����
        this._radian = 0;//��ת�任����
        this._support = false;//�Ƿ�֧�ֱ任
        this._init = this._load = this._show = this._dispose = $$.emptyFunction;

        var opt = this._setOptions(options);

        this._zoom = opt.zoom;

        this.onPreLoad = opt.onPreLoad;
        this.onLoad = opt.onLoad;
        this.onError = opt.onError;

        this._LOAD = $$F.bind(function () {
            this.onLoad(); this._load(); this.reset();
            this._img.style.visibility = "visible";
        }, this);

        $$CE.fireEvent(this, "init");
    },
    //����Ĭ������
    _setOptions: function (options) {
        this.options = {//Ĭ��ֵ
            mode: "css3|filter|canvas",
            zoom: .1,//���ű���
            onPreLoad: function () { },//ͼƬ����ǰִ��
            onLoad: function () { },//ͼƬ���غ�ִ��
            onError: function (err) { }//����ʱִ��
        };
        return $$.extend(this.options, options || {});
    },
    //ģʽ����
    _initMode: function () {
        var modes = ImageTrans.modes;
        this._support = $$A.some(this.options.mode.toLowerCase().split("|"), function (mode) {
            mode = modes[mode];
            if (mode && mode.support) {
                mode.init && (this._init = mode.init);//��ʼ��ִ�г���
                mode.load && (this._load = mode.load);//����ͼƬִ�г���
                mode.show && (this._show = mode.show);//�任��ʾ����
                mode.dispose && (this._dispose = mode.dispose);//��ٳ���
                //��չ�任����
                $$A.forEach(ImageTrans.transforms, function (transform, name) {
                    this[name] = function () {
                        transform.apply(this, [].slice.call(arguments));
                        this._show();
                    }
                }, this);
                return true;
            }
        }, this);
    },
    //��ʼ����������
    _initContainer: function () {
        var container = this._container, style = container.style, position = $$D.getStyle(container, "position");
        this._style = { "position": style.position, "overflow": style.overflow };//������ʽ
        if (position != "relative" && position != "absolute") { style.position = "relative"; }
        style.overflow = "hidden";
        $$CE.fireEvent(this, "initContainer");
    },
    //����ͼƬ
    load: function (src) {
        if (this._support) {
            var img = this._img, oThis = this;
            img.onload || (img.onload = this._LOAD);
            img.onerror || (img.onerror = function () { oThis.onError("err image"); });
            img.style.visibility = "hidden";
            this.onPreLoad();
            img.src = src;
        }
    },
    //����
    reset: function () {
        if (this._support) {
            this._x = this._y = 1; this._radian = 0;
            this._show();
        }
    },
    //��ٳ���
    dispose: function () {
        if (this._support) {
            this._dispose();
            $$CE.fireEvent(this, "dispose");
            $$D.setStyle(this._container, this._style);//�ָ���ʽ
            this._container = this._img = this._img.onload = this._img.onerror = this._LOAD = null;
        }
    }
};
//�任ģʽ
ImageTrans.modes = function () {
    var css3Transform;//ccs3�任��ʽ
    //��ʼ��ͼƬ������
    function initImg(img, container) {
        $$D.setStyle(img, {
            position: "absolute",
            border: 0, padding: 0, margin: 0, width: "auto", height: "auto",//������ʽ
            visibility: "hidden"//����ǰ����
        });
        container.appendChild(img);
    }
    //��ȡ�任������
    function getMatrix(radian, x, y) {
        var Cos = Math.cos(radian), Sin = Math.sin(radian);
        return {
            M11: Cos * x, M12: -Sin * y,
            M21: Sin * x, M22: Cos * y
        };
    }
    return {
        css3: {//css3����
            support: function () {
                var style = document.createElement("div").style;
                return $$A.some(
					["transform", "MozTransform", "webkitTransform", "OTransform"],
					function (css) {
					    if (css in style) {
					        css3Transform = css; return true;
					    }
					});
            }(),
            init: function () { initImg(this._img, this._container); },
            load: function () {
                var img = this._img;
                $$D.setStyle(img, {//����
                    top: img.height == 0 ?0+ "px" : (this._clientHeight - img.height) / 2 + "px",
                    left: img.width == 0 ? 0+ "px" : (this._clientWidth - img.width) / 2 + "px",
                    visibility: "visible"
                });
            },
            show: function () {
                var matrix = getMatrix(this._radian, this._y, this._x);
                //���ñ�����ʽ
                this._img.style[css3Transform] = "matrix("
					+ matrix.M11.toFixed(16) + "," + matrix.M21.toFixed(16) + ","
					+ matrix.M12.toFixed(16) + "," + matrix.M22.toFixed(16) + ", 0, 0)";
            },
            dispose: function () { this._container.removeChild(this._img); }
        },
        filter: {//�˾�����
            support: function () { return "filters" in document.createElement("div"); }(),
            init: function () {
                initImg(this._img, this._container);
                //�����˾�
                this._img.style.filter = "progid:DXImageTransform.Microsoft.Matrix(SizingMethod='auto expand')";
            },
            load: function () {
                this._img.onload = null;//��ֹie�ظ�����gif��bug
                this._img.style.visibility = "visible";
            },
            show: function () {
                var img = this._img;
                //�����˾�
                $$.extend(
					img.filters.item("DXImageTransform.Microsoft.Matrix"),
					getMatrix(this._radian, this._y, this._x)
				);
                //���־���
                img.style.top = (this._clientHeight - img.offsetHeight) / 2 + "px";
                img.style.left = (this._clientWidth - img.offsetWidth) / 2 + "px";
            },
            dispose: function () { this._container.removeChild(this._img); }
        },
        canvas: {//canvas����
            support: function () { return "getContext" in document.createElement('canvas'); }(),
            init: function () {
                var canvas = this._canvas = document.createElement('canvas'),
					context = this._context = canvas.getContext('2d');
                //��ʽ����
                $$D.setStyle(canvas, { position: "absolute", left: 0, top: 0 });
                canvas.width = this._clientWidth; canvas.height = this._clientHeight;
                this._container.appendChild(canvas);
            },
            show: function () {
                var img = this._img, context = this._context,
					clientWidth = this._clientWidth, clientHeight = this._clientHeight;
                //canvas�任
                context.save();
                context.clearRect(0, 0, clientWidth, clientHeight);//�������
                context.translate(clientWidth / 2, clientHeight / 2);//�������
                context.rotate(this._radian);//��ת
                context.scale(this._y, this._x);//����
                context.drawImage(img, -img.width / 2, -img.height / 2);//���л�ͼ
                context.restore();
            },
            dispose: function () {
                this._container.removeChild(this._canvas);
                this._canvas = this._context = null;
            }
        }
    };
}();
//�任����
ImageTrans.transforms = {
    //��ֱ��ת
    vertical: function () {
        this._radian = Math.PI - this._radian; this._y *= -1;
    },
    //ˮƽ��ת
    horizontal: function () {
        this._radian = Math.PI - this._radian; this._x *= -1;
    },
    //��ݻ�����ת
    rotate: function (radian) { this._radian = radian; },
    //����ת90��
    left: function () { this._radian -= Math.PI / 2; },
    //����ת90��
    right: function () { this._radian += Math.PI / 2; },
    //��ݽǶ���ת
    rotatebydegress: function (degress) { this._radian = degress * Math.PI / 180; },
    //����
    scale: function () {
        function getZoom(scale, zoom) {
            return scale > 0 && scale > -zoom ? zoom :
                    scale < 0 && scale < zoom ? -zoom : 0;
        }
        return function (zoom) {
            if (zoom) {
                var hZoom = getZoom(this._y, zoom), vZoom = getZoom(this._x, zoom);
                if (hZoom && vZoom) {
                    this._y += hZoom; this._x += vZoom;
                }
            }
        }
    }(),
    //�Ŵ�
    zoomin: function () { this.scale(Math.abs(this._zoom)); },
    //��С
    zoomout: function () { this.scale(-Math.abs(this._zoom)); }
};


//�϶���ת��չ

ImageTrans.prototype._initialize = (function () {
    var init = ImageTrans.prototype._initialize,
		methods = {
		    "init": function () {
		        //this._mrX = this._mrY = this._mrRadian = 0;
		        this._mrSTART = $$F.bind(start, this);
		        this._mrMOVE = $$F.bind(move, this);
		        this._mrSTOP = $$F.bind(stop, this);
		    },
		    "initContainer": function () {
		        $$E.addEvent(this._container, "mousedown", this._mrSTART);
		    },
		    "dispose": function () {
		        $$E.removeEvent(this._container, "mousedown", this._mrSTART);
		        this._mrSTOP();
		        this._mrSTART = this._mrMOVE = this._mrSTOP = null;
		    }
		};
    var x, y;
    var nn6;
    //��ʼ����
    function start(e) {
        //var rect = $$D.clientRect(this._container);
        //this._mrX = rect.left + this._clientWidth / 2;
        //this._mrY = rect.top + this._clientHeight / 2;
        //this._mrRadian = Math.atan2(e.clientY - this._mrY, e.clientX - this._mrX) - this._radian;
        this._mrX = parseInt(this._img.style.left);
        this._mrY = parseInt(this._img.style.top);

        nn6 = document.getElementById && !document.all;
        y = nn6 ? e.clientY : event.clientY;
        x = nn6 ? e.clientX : event.clientX;
        $$E.addEvent(document, "mousemove", this._mrMOVE);
        $$E.addEvent(document, "mouseup", this._mrSTOP);
        if ($$B.ie) {
            var container = this._container;
            $$E.addEvent(container, "losecapture", this._mrSTOP);
            container.setCapture();
        } else {
            $$E.addEvent(window, "blur", this._mrSTOP);
            e.preventDefault();
        }
    };
    //�϶�����

    function move(e) {
        ry = (nn6 ? this._mrY + e.clientY - y : this._mrY + event.clientY - y) + "px";
        rx = (nn6 ? this._mrX + e.clientX - x : this._mrX + event.clientX - x) + "px";
        this._img.style.top = ry;
        this._img.style.left = rx;

        //console.log("Y" + this._mrY + "     X " + this._mrX + "    EY" + e.clientY + "   EX" + e.clientX + "    x" + x + "    y" + y+"    ry"+ry+"    rx"+rx);
        //this.rotate(Math.atan2(e.clientY - this._mrY, e.clientX - this._mrX) - this._mrRadian);
        // window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
    };
    //ֹͣ����
    function stop() {
        $$E.removeEvent(document, "mousemove", this._mrMOVE);
        $$E.removeEvent(document, "mouseup", this._mrSTOP);
        if ($$B.ie) {
            var container = this._container;
            $$E.removeEvent(container, "losecapture", this._mrSTOP);
            container.releaseCapture();
        } else {
            $$E.removeEvent(window, "blur", this._mrSTOP);
        };
    };
    return function () {
        var options = arguments[1];
        if (!options || options.mouseRotate !== false) {
            //��չ����
            $$A.forEach(methods, function (method, name) {
                $$CE.addEvent(this, name, method);
            }, this);
        }
        init.apply(this, arguments);
    }
})();



//����������չ
ImageTrans.prototype._initialize = (function () {
    var init = ImageTrans.prototype._initialize,
		mousewheel = $$B.firefox ? "DOMMouseScroll" : "mousewheel",
		methods = {
		    "init": function () {
		        this._mzZoom = $$F.bind(zoom, this);
		    },
		    "initContainer": function () {
		        $$E.addEvent(this._container, mousewheel, this._mzZoom);
		    },
		    "dispose": function () {
		        $$E.removeEvent(this._container, mousewheel, this._mzZoom);
		        this._mzZoom = null;
		    }
		};
    //���ź���
    function zoom(e) {
        this.scale((
			e.wheelDelta ? e.wheelDelta / (-120) : (e.detail || 0) / 3
		) * Math.abs(this._zoom));
        e.preventDefault();
    };
    return function () {
        var options = arguments[1];
        if (!options || options.mouseZoom !== false) {
            //��չ����
            $$A.forEach(methods, function (method, name) {
                $$CE.addEvent(this, name, method);
            }, this);
        }
        init.apply(this, arguments);
    }
})();