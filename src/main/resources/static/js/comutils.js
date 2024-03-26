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
                if (xhr.status === "403") {
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
                if (xhr.status === "403") {
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
     * 날짜 변환
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
}

// -- 정규식 --
// 아이디: 영문소문자,-,_,.숫자
const isLoginId = (str) => {
    const regex = /^[a-z\-_.0-9]{6,16}$/;
    return regex.test(str);
}
// 비밀번호 : 숫자,문자,특수문자 혼용 8~20자
const isPassword = (str) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    return regex.test(str);
}
// 이름 : 영문대소문자,한글완성형,특수문자허용 3~8자
const isName = (str) => {
    const regex = /^[a-zA-Z가-힣?=.*\[~!@#$%^&()\-_+\]]{3,8}$/;
    return regex.test(str);
}
// 이메일 : 이메일형식
const isEmail = (str) => {
    const regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,}$/i;
    return regex.test(str);
}
// 휴대전화번호 : 숫자 10~11자
const isMobile = (str) => {
    const regex = /^[0-9]{10,11}$/;
    return regex.test(str);
}

/**
 * 쿠키 생성
 * @param key 쿠키 key
 * @param value 쿠키 value
 * @param day 쿠키 만료일(일자)
 * @param path 쿠키 저장 경로
 * @param domain 쿠키 저장 도메인
 * @param secure 보안 설정
 */
const setCookie = (key, value, day = 365, path = '/', domain = '', secure = false) => {
    let date = new Date();
    date.setTime(date.getTime() + day * 24 * 60 * 60 * 1000);
    let cookieString = `${key}=${encodeURIComponent(value)};expires=${date.toUTCString()};path=${path}`;
    if (domain) cookieString += `;domain=${domain}`;
    if (secure) cookieString += ';secure';
    document.cookie = cookieString;
}

/**
 * 쿠키 값 가져오기
 * @param key
 * @returns {string|null}
 */
const getCookie = (key) => {
    let value = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return value ? decodeURIComponent(value[2]) : null;
}

const comma = (str) => {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

const uncomma = (str) => {
    str = String(str);
    return str.replace(/\D+/g, '');
}

const inputNumberFormat = (obj) => {
    obj.value = obj.comma(uncomma(obj.value));
}

const inputNumberOnly = (self) => {
    self.value = self.value.replace(/[^0-9.]/g, '');
    self.value = self.value.replace(/(\..*)\./g, '$1');
    self.value = self.value.replace(/^(\d+)\.(\D*)$/, '$1');
}

const inputFormatMobile = (mobile) => {
    if (!mobile || mobile.trim() === "") {
        return "";
    }
    return mobile.replace(/^(\d{3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

const isImage = (file) => {
    let imageExt = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp', 'svg'];
    let ext = file.type
        .split('/')
        .pop()
        .toLowerCase();
    return imageExt.find(i => i === ext);
}

const spin = {
    show() {
        $("#spinLoading").show();
    },
    hide() {
        $("#spinLoading").hide();
    }
}

const jsonToQueryString = (json) => {
    return new URLSearchParams(json);
}

const queryStringToJson = (queryString) => {
    let json = {};
    queryString.split('&').forEach(pair => {
        let [key, value] = pair.split('=');
        json[decodeURIComponent(key)] = decodeURIComponent(value || '');
    });
    return json;
}

const ajax = {
    requestCount: 0,
    defaultHeaders: {
        'Content-type': 'application/json; charset=UTF-8'
    },
    spinShow: () => {
        if (ajax.requestCount === 0) {
            spin.show();
        }
        ajax.requestCount++;
    },
    spinHide: () => {
        ajax.requestCount--;
        if (ajax.requestCount === 0) {
            spin.hide();
        }
    },
    sendRequest: async (url, options) => {
        ajax.spinShow();
        return await new Promise((resolve, reject) => {
            $.ajax({
                ...options,
                url: url,
                success: function (res) {
                    resolve(res);
                },
                error: function(ex) {
                    if (403 === parseInt(ex.status)) {
                        document.location.href = "/login";
                    } else {
                        reject(ex);
                    }
                },
                complete: function () {
                    ajax.spinHide();
                }
            });
        });
    },
    get: async (url, params, headers = {}) => {
        const options = {
            type: "GET",
            data: params,
            headers: { ...headers },
        }
        return ajax.sendRequest(url, options);
    },
    post: async (url, data, headers = ajax.defaultHeaders) => {
        const options = {
            type: "POST",
            data: JSON.stringify(data),
            headers: { ...headers },
        }
        return ajax.sendRequest(url, options);
    },
    put: async (url, data, headers = ajax.defaultHeaders) => {
        const options = {
            type: "PUT",
            data: JSON.stringify(data),
            headers: { ...headers },
        }
        return ajax.sendRequest(url, options);
    },
    delete: async (url, params, headers = {}) => {
        const options = {
            type: "DELETE",
            data: params,
            headers: { ...headers },
        }
        return ajax.sendRequest(url, options);
    },
    file: async (url, files) => {
        const formData = new FormData();
        files.forEach(file => formData.append("file", file));

        const options = {
            type: "POST",
            processData: false,
            contentType: false,
            data: formData
        }
        return ajax.sendRequest(url, options);
    }
};

const apiReq = {
    requestCount: 0,
    defaultHeaders: {
        'Content-Type': 'application/json; charset=UTF-8'
    },
    spinShow: () => {
        if (apiReq.requestCount === 0) {
            spin.show();
        }
        apiReq.requestCount++;
    },
    spinHide: () => {
        apiReq.requestCount--;
        if (apiReq.requestCount === 0) {
            spin.hide();
        }
    },
    sendRequest: async (url, options) => {
        apiReq.spinShow();
        const res = await fetch(url, options);
        apiReq.spinHide();
        if (!res.ok) {
            if (403 === res.status) {
                document.location.href = "/login";
            } else {
                throw new Error(`Fetch request failed: ${res}`);
            }
        }
        return await res.json();
    },
    get: async (url, params = '', headers = {}) => {
        const queryString = `?${new URLSearchParams(params).toString()}`;
        const options = {
            method: "GET",
            headers: headers,
        };
        return apiReq.sendRequest(`${url}${queryString}`, options);
    },
    post: async (url, data, headers = apiReq.defaultHeaders) => {
        const options = {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        };
        return apiReq.sendRequest(url, options);
    },
    put: async (url, data, headers = apiReq.defaultHeaders) => {
        const options = {
            method: "PUT",
            headers: headers,
            body: JSON.stringify(data)
        };
        return apiReq.sendRequest(url, options);
    },
    delete: async (url, params = '', headers = {}) => {
        const queryString = `?${new URLSearchParams(params).toString()}`;
        const options = {
            method: "DELETE",
            headers: headers,
        };
        return apiReq.sendRequest(`${url}${queryString}`, options);
    },
    file: async (url, files) => {
        const formData = new FormData();
        files.forEach(file => formData.append("file", file));

        const options = {
            method: "POST",
            body: formData
        };
        return apiReq.sendRequest(url, options);
    }
};

$(document).ready(function () {

})