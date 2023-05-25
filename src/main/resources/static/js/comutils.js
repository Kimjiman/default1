const com = {
    ajax: function (obj, callbackFnc) {
        const options = {
            type: "GET",
            async: true,
            data: obj.params,
            success: function (response) {
                if (typeof callbackFnc === "function") {
                    callbackFnc(response);
                }
            },
            error: function (xhr) {
                if (xhr.status == "403") {
                    document.location.href = "/login";
                }
                /* else if(2000 <= Number(xhr.status) &&  Number(xhr.status) < 3000) {
                    return alert(xhr.responseJSON.message);
                 } */
                else {
                    console.log(xhr);
                }
            }
        };
        $.extend(options, obj);
        $.ajax(options);
    },

    ajaxFile: function (obj, callbackFnc) {
        $.ajax({
            type: "POST",
            async: obj.async,
            url: obj.url,
            data: obj.data,
            processData: false,
            contentType: false,
            success: function (response) {
                if (typeof callbackFnc === "function") {
                    callbackFnc(response);
                }
            },
            error: function (xhr) {
                if (xhr.status == "403") {
                    document.location.href = "/login";
                } else {
                    console.log(xhr);
                }
            }
        });

    },

    isNumber(str) {
        const regx = /^[0-9]+$/;
        return regx.test(str);
    },

    /**
     * Null 체크
     * @param {*} value
     */
    isEmpty: value => {
        return value === "" || value === null || (typeof value == "object" && !Object.keys(value).length);
    },
    cloneObject(obj) {
        let clone = {};
        for (var key in obj) {
            if (typeof obj[key] == "object" && obj[key] != null) {
                clone[key] = this.cloneObject(obj[key]);
            } else {
                clone[key] = obj[key];
            }
        }
        return clone;
    },
    /**
     * 숫자 콤마
     * @param {숫자} num
     * @returns
     */
    addComma(num) {
        if (!num) {
            return "";
        }
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },

    /**
     * 날자 변환
     * yyyy-MM-dd, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd a/p hh:mm:ss, yyyy-MM-dd E
     * @param {날짜 Date} format
     * @param date
     * @returns
     */
    getDate(format, date) {

        if (date == null) return "";

        let weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
        let d = new Date(date);
        let h;
        String.prototype.string = function (len) {
            var s = '', i = 0;
            while (i++ < len) {
                s += this;
            }
            return s;
        };
        String.prototype.zf = function (len) {
            return "0".string(len - this.length) + this;
        };
        Number.prototype.zf = function (len) {
            return this.toString().zf(len);
        };

        return format.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function ($1) {
            switch ($1) {
                case "yyyy":
                    return d.getFullYear();
                case "yy":
                    return (d.getFullYear() % 1000).zf(2);
                case "MM":
                    return (d.getMonth() + 1).zf(2);
                case "dd":
                    return d.getDate().zf(2);
                case "E":
                    return weekName[d.getDay()];
                case "HH":
                    return d.getHours().zf(2);
                // case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
                case "hh":
                    return ((h = d.getHours() % 12) ? h : 12);
                case "mm":
                    return d.getMinutes().zf(2);
                case "ss":
                    return d.getSeconds().zf(2);
                case "a/p":
                    return d.getHours() < 12 ? "오전" : "오후";
                default:
                    return $1;
            }
        });
    },

    addDate(date, interval, units) {
        if (!(date instanceof Date)) return undefined;

        let ret = new Date(date); //don't change original date
        let checkRollover = function () {
            if (ret.getDate() !== date.getDate()) ret.setDate(0);
        };
        switch (String(interval).toLowerCase()) {
            case 'year'   :
                ret.setFullYear(ret.getFullYear() + units);
                checkRollover();
                break;
            case 'quarter':
                ret.setMonth(ret.getMonth() + 3 * units);
                checkRollover();
                break;
            case 'month'  :
                ret.setMonth(ret.getMonth() + units);
                checkRollover();
                break;
            case 'week'   :
                ret.setDate(ret.getDate() + 7 * units);
                break;
            case 'day'    :
                ret.setDate(ret.getDate() + units);
                break;
            case 'hour'   :
                ret.setTime(ret.getTime() + units * 3600000);
                break;
            case 'minute' :
                ret.setTime(ret.getTime() + units * 60000);
                break;
            case 'second' :
                ret.setTime(ret.getTime() + units * 1000);
                break;
            default       :
                ret = undefined;
                break;
        }
        return ret;
    },

    // -- 정규식 --
    // 아이디: 영문소문자,-,_,.숫자
    isLoginId(str) {
        const regex = /^[a-z\-_.0-9]{6,16}$/;
        return regex.test(str);
    },
    // 비밀번호 : 숫자,문자,특수문자 혼용 8~16자
    isPassword(str) {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
        return regex.test(str);
    },
    // 이름 : 영문대소문자,한글완성형,특수문자허용 3~8자
    isName(str) {
        const regex = /^[a-zA-Z가-힣?=.*\[~!@#$%^&()\-_+\]]{3,8}$/;
        return regex.test(str);
    },
    // 이메일 : 이메일형식
    isEmail(str) {
        const regex = /[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;
        return regex.test(str);
    },
    // 휴대전화번호 : 숫자 10~11자
    isMobile(str) {
        const regex = /^[0-9]{10,11}$/;
        return regex.test(str);
    },

    setCookie(key, value, expires) {
        let date = new Date();
        let path = window.location.pathname;    // 쿠키에 접근가능한 도메인경로 세팅
        //console.log(date.toUTCString());      // 쿠키 생성시 시간
        date.setTime(date.getTime() + expires * 24 * 60 * 60 * 1000);
        //console.log(date.toUTCString());      // 쿠키 만료시 시간
        document.cookie = key + '=' + value + ';expires=' + date.toUTCString() + ';path=' + path;
    },
    getCookie(key) {
        let value = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    },

    comma(str) {
        str = String(str);
        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    },

    uncomma(str) {
        str = String(str);
        return str.replace(/\D+/g, '');
    },

    inputNumberFormat(obj) {
        obj.value = this.comma(this.uncomma(obj.value));
    },

    inputNumberOnly(value) {
        return value.replace(/\D/g, '').replace(/(\..*)\./g, '$1');
    },

    inputFormatMobile(mobile) {
        if (!mobile || mobile.trim() === "") {
            return "";
        }
        return mobile.replace(/^(\d{3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
    },

    isImage(file) {
        let imageExt = ['jpg', 'jpeg', 'png'];
        let ext = file.type
            .split('/')
            .pop()
            .toLowerCase();
        return imageExt.find(i => i === ext);
    },
}

/**
 * form data To Object
 */
jQuery.fn.serializeObject = function () {
    let obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() === "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function () {
                    obj[this.name] = this.value;
                });
            }
        }
    } catch (e) {
        alert(e.message);
    }
    return obj;
};

const spin = {
    show() {
        $("#spinLoading").show();
    },
    hide() {
        $("#spinLoading").hide();
    }
}

$(document).ready(function () {

})