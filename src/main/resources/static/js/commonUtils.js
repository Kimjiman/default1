$(document).ready(function () {

})

/**
 * 깊은 복사
 * @param obj
 * @returns {{}}
 */
const deepCopy = (obj) => {
    if (obj === null || typeof obj !== 'object') {
        return obj;
    }

    const result = Array.isArray(obj) ? [] : {};

    for (let prop in obj) {
        if (obj.hasOwnProperty(prop)) {
            result[prop] = deepCopy(obj[prop]);
        }
    }

    return result;
};

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
    const date = new Date();
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
    const value = document.cookie.match(`(^|;) ?${key}=([^;]*)(;|$)`);
    return value ? decodeURIComponent(value[2]) : null;
}

/**
 * 쿠키를 삭제한다.
 * @param key
 */
const deleteCookie = (key) => {
    setCookie(key, null, 0);
}

/**
 * 숫자 통화형식으로 쉼표를붙인다
 * @param str
 * @returns {string}
 */
const comma = (str) => {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

/**
 * 숫자에서 쉼표를 제거한다.
 * @param str
 * @returns {string}
 */
const uncomma = (str) => {
    str = String(str);
    return str.replace(/\D+/g, '');
}

const inputNumberFormat = (obj) => {
    obj.value = obj.comma(uncomma(obj.value));
}

/**
 * input 칸을 숫자만 가능하게 변경한다.
 * @param self
 */
const inputNumberOnly = (self) => {
    self.value = self.value
        .replace(/[^0-9.]/g, '')
        .replace(/(\..*)\./g, '$1')
        .replace(/^(\d+)\.(\D*)$/, '$1');
}

/**
 * mobile형식으로 전화번호를 변경한다.
 * @param val
 * @returns {*|string}
 */
const inputMobileFormat = (val) => {
    if (stringUtils.isEmpty(val)) {
        return "";
    }
    return val.replace(/^(\d{3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

/**
 * 이미지 확장자로 이미지 체크
 * @param file
 * @returns {string}
 */
const isImage = (file) => {
    const imageExt = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp', 'svg'];
    let ext = file.type
        .split('/')
        .pop()
        .toLowerCase();
    return imageExt.find(i => i === ext);
}

/**
 * 초를 시간형식으로 변경한다.
 * @param second
 * @returns {string}
 */
const secondToTime = (second) => {
    let date = new Date(0);
    date.setSeconds(second);
    return date.toISOString().substring(14, 19);
}

/**
 * 현재 페이지의 url을 카피한다.
 * @param msg
 * @returns {Promise<void>}
 */
const clipUrl = async (msg = null) => {
    let url = window.document.location.href;
    await navigator.clipboard.writeText(url);
    if (msg) alert(msg);
};

/**
 * 스크롤링을 위한 함수
 * @param func 콜백함수
 * @param scrollRange 스크롤 감지 비율
 * @returns {Promise<void>}
 */
let isAtTheBottom = false;
const scrollEndPoint = async (func, scrollRange = 0.8) => {
    if(typeof func !== 'function') {
        throw new Error("Type 'func' must require a function.")
    }
    let scroll = document.documentElement;
    const { scrollHeight, scrollTop, clientHeight } = scroll;
    if ((scrollHeight - clientHeight) * scrollRange <= scrollTop) {
        if (!isAtTheBottom) {
            isAtTheBottom = true;
            await func();
        }
    } else if (isAtTheBottom) {
        isAtTheBottom = false;
    }
}

/**
 * form을 submit한다.
 * @param querySelector
 * @param action
 * @param method
 */
const formSubmit = (querySelector, action, method = "GET") => {
    let frm = document.querySelector(querySelector);
    frm.action = action;
    frm.method = method;
    frm.submit();
}

/**
 * form안에 name을 기반으로 json 데이터를 생성한다.
 * @param querySelector 선택자
 * @returns {{}}
 */
const serializeObject = (querySelector) => {
    let obj = {};
    const form = document.querySelector(querySelector);
    const formData = new FormData(form);
    formData.forEach((value, key) => {
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
            if (!Array.isArray(obj[key])) {
                obj[key] = [obj[key]];
            }
            obj[key].push(value);
        } else {
            obj[key] = value;
        }
    });
    return obj;
}

/**
 * jquery선택자를 이용해서 form안에 name을 기반으로 json 데이터를 생성한다.
 * @returns {null}
 */
jQuery.fn.serializeObject = function () {
    let obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() === "FORM") {
            let arr = this.serializeArray();
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

const ajaxApi = {
    requestCount: 0,
    defaultHeaders: {
        'Content-type': 'application/json; charset=UTF-8'
    },
    spinShow: () => {
        if (ajaxApi.requestCount === 0) {
            spin.show();
        }
        ajaxApi.requestCount++;
    },
    spinHide: () => {
        ajaxApi.requestCount--;
        if (ajaxApi.requestCount === 0) {
            spin.hide();
        }
    },
    sendRequest: async (url, options) => {
        ajaxApi.spinShow();
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
                    ajaxApi.spinHide();
                }
            });
        });
    },
    get: async (url, params = {}, headers = {}) => {
        const options = {
            type: "GET",
            data: params,
            headers: { ...headers },
        }
        return ajaxApi.sendRequest(url, options);
    },
    post: async (url, data = {}, headers = ajaxApi.defaultHeaders) => {
        const options = {
            type: "POST",
            data: JSON.stringify(data),
            headers: { ...headers },
        }
        return ajaxApi.sendRequest(url, options);
    },
    put: async (url, data = {}, headers = ajaxApi.defaultHeaders) => {
        const options = {
            type: "PUT",
            data: JSON.stringify(data),
            headers: { ...headers },
        }
        return ajaxApi.sendRequest(url, options);
    },
    delete: async (url, params = {}, headers = {}) => {
        const options = {
            type: "DELETE",
            data: params,
            headers: { ...headers },
        }
        return ajaxApi.sendRequest(url, options);
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
        return ajaxApi.sendRequest(url, options);
    }
};

const fetchApi = {
    requestCount: 0,
    defaultHeaders: {
        'Content-Type': 'application/json; charset=UTF-8'
    },
    spinShow: () => {
        if (fetchApi.requestCount === 0) {
            spin.show();
        }
        fetchApi.requestCount++;
    },
    spinHide: () => {
        fetchApi.requestCount--;
        if (fetchApi.requestCount === 0) {
            spin.hide();
        }
    },
    sendRequest: async (url, options) => {
        fetchApi.spinShow();
        const res = await fetch(url, options);
        fetchApi.spinHide();
        if (!res.ok) {
            if (403 === res.status) {
                document.location.href = "/login";
            } else {
                throw new Error(`${res}`);
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
        return fetchApi.sendRequest(`${url}${queryString}`, options);
    },
    post: async (url, data = {}, headers = fetchApi.defaultHeaders) => {
        const options = {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        };
        return fetchApi.sendRequest(url, options);
    },
    put: async (url, data = {}, headers = fetchApi.defaultHeaders) => {
        const options = {
            method: "PUT",
            headers: headers,
            body: JSON.stringify(data)
        };
        return fetchApi.sendRequest(url, options);
    },
    delete: async (url, params = '', headers = {}) => {
        const queryString = `${new URLSearchParams(params).toString()}`;
        const options = {
            method: "DELETE",
            headers: headers,
        };
        if(stringUtils.isNotEmpty(params)) {
            url = `${url}?${queryString}`;
        }
        return fetchApi.sendRequest(url, options);
    },
    file: async (url, files) => {
        const formData = new FormData();
        files.forEach(file => formData.append("file", file));

        const options = {
            method: "POST",
            body: formData
        };
        return fetchApi.sendRequest(url, options);
    }
};

const stringUtils = {
    /**
     * val가 empty 일때
     * @param val
     * @returns {boolean}
     */
    isEmpty: (val) => {
        if (val === null || val === undefined || val === "") {
            return true;
        }

        if (Array.isArray(val) || typeof val === 'object') {
            return Object.keys(val).length === 0;
        }

        return false;
    },

    /**
     * val가 empty 아닐때
     * @param val
     * @returns {boolean}
     */
    isNotEmpty: (val) => {
        return !stringUtils.isEmpty(val);
    },

    /**
     * val가 empty일때 replaceVal로 대체
     * @param val
     * @param replaceVal
     * @returns {*}
     */
    ifEmpty: (val, replaceVal) => {
        return stringUtils.isEmpty(val) ? replaceVal : val;
    },

    /**
     * json을 queryString으로 변환
     * @param json
     * @returns {URLSearchParams}
     */
    jsonToQueryString: (json) => {
        return new URLSearchParams(json);
    },

    /**
     * queryString을 json으로 변환
     * @param queryString
     * @returns {{}}
     */
    queryStringToJson: (queryString) => {
        let json = {};
        queryString.split('&').forEach(pair => {
            let [key, value] = pair.split('=');
            json[decodeURIComponent(key)] = decodeURIComponent(value || '');
        });
        return json;
    },

    /**
     * 정규식
     * @param val
     * @param regex
     * @returns {boolean}
     */
    isRegex: (val, regex) => {
        if(stringUtils.isEmpty(val) || stringUtils.isEmpty(regex)) return false;
        return regex.test(val);
    },

    /**
     * 영어소문자, 숫자, -_로 아이디 만들기
     * @param val
     * @returns {boolean}
     */
    isLoginId: (val) => {
        const regex = /^[a-z\-_.0-9]{6,20}$/;
        return stringUtils.isRegex(val, regex);
    },

    /**
     * 대소문자, 특수문자, 숫자로 비밀번호
     * @param val
     * @returns {boolean}
     */
    isPassword: (val) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
        return stringUtils.isRegex(val, regex);
    },

    /**
     * 영문대소문자,한글완성형,특수문자허용 3~8자
     * @param val
     * @returns {boolean}
     */
    isName: (val) => {
        const regex = /^[a-zA-Z가-힣?=.*\[~!@#$%^&()\-_+\]]{3,8}$/;
        return stringUtils.isRegex(val, regex);
    },

    /**
     * 이메일
     * @param val
     * @returns {boolean}
     */
    isEmail: (val) => {
        const regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,}$/i;
        return stringUtils.isRegex(val, regex);
    },

    /**
     * 휴대폰 번호 숫자만 10~11자
     * @param val
     * @returns {boolean}
     */
    isMobile: (val) => {
        const regex = /^[0-9]{10,11}$/;
        return stringUtils.isRegex(val, regex);
    },

    /**
     * 숫자체크
     * @param val
     * @returns {boolean}
     */
    isNumber: (val) => {
        const regex = /^[0-9]+$/;
        return stringUtils.isRegex(val, regex);
    },
}

const dateUtils = {
    datePattern: {
        DATE_TIME_FORMAT: "yyyy-MM-dd HH:mm:ss",
        DATE_FORMAT: "yyyy-MM-dd",
        TIME_FORMAT: "HH:mm:ss"
    },
    /**
     * 주어진 날짜를 pattern 형식으로 변경한다.
     * @param date
     * @param pattern
     * @returns {*}
     */
    formatDate: (date, pattern) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        return pattern
            .replace('yyyy', year)
            .replace('MM', month)
            .replace('dd', day)
            .replace('HH', hours)
            .replace('mm', minutes)
            .replace('ss', seconds);
    },

    getDateTimeString: (date) => {
        return dateUtils.formatDate(date, dateUtils.datePattern.DATE_TIME_FORMAT);
    },

    getDateString: (date) => {
        return dateUtils.formatDate(date, dateUtils.datePattern.DATE_FORMAT);
    },

    getTimeString: (date) => {
        return dateUtils.formatDate(date, dateUtils.datePattern.TIME_FORMAT);
    },

    getDayOfWeekNumber: (date) => {
        let newDate = new Date(date);
        return newDate.getDay();
    },
    getDayOfWeekString: (date) => {
        const dayOfWeek = {
            0: "월",
            1: "화",
            2: "수",
            3: "목",
            4: "금",
            5: "토",
            6: "일"
        }
        return dayOfWeek[dateUtils.getDayOfWeekNumber(date)];
    },

    plusYears: (date, years) => {
        const newDate = new Date(date);
        newDate.setFullYear(date.getFullYear() + years);
        return newDate;
    },

    minusYears: (date, years) => {
        const newDate = new Date(date);
        newDate.setFullYear(date.getFullYear() - years);
        return newDate;
    },

    plusMonths: (date, months) => {
        const newDate = new Date(date);
        newDate.setMonth(date.getMonth() + months);
        return newDate;
    },

    minusMonths: (date, months) => {
        const newDate = new Date(date);
        newDate.setMonth(date.getMonth() - months);
        return newDate;
    },

    plusDays: (date, days) => {
        const newDate = new Date(date);
        newDate.setDate(date.getDate() + days);
        return newDate;
    },

    minusDays: (date, days) => {
        const newDate = new Date(date);
        newDate.setDate(date.getDate() - days);
        return newDate;
    },

    plusHours: (date, hours) => {
        const newDate = new Date(date);
        newDate.setHours(date.getHours() + hours);
        return newDate;
    },

    minusHours: (date, hours) => {
        const newDate = new Date(date);
        newDate.setHours(date.getHours() - hours);
        return newDate;
    },

    plusMinutes: (date, minutes) => {
        const newDate = new Date(date);
        newDate.setMinutes(date.getMinutes() + minutes);
        return newDate;
    },

    minusMinutes: (date, minutes) => {
        const newDate = new Date(date);
        newDate.setMinutes(date.getMinutes() - minutes);
        return newDate;
    },

    plusSeconds: (date, seconds) => {
        const newDate = new Date(date);
        newDate.setSeconds(date.getSeconds() + seconds);
        return newDate;
    },

    minusSeconds: (date, seconds) => {
        const newDate = new Date(date);
        newDate.setSeconds(date.getSeconds() - seconds);
        return newDate;
    },
}

const arrayUtils = {
    removeElementByValue: (arr, val) => {
        if(stringUtils.isEmpty(arr) || stringUtils.isEmpty(val)) return arr;
        const newArr = Object.assign([], arr);
        const index = newArr.indexOf(val);
        if (index !== -1) {
            newArr.splice(index, 1);
        }
        return newArr;
    },

    removeElementByIndex: (arr, index) => {
        if (stringUtils.isEmpty(arr) || index < 0 || index >= arr.length) return arr;
        const newArr = Object.assign([], arr);
        newArr.splice(index, 1);
        return newArr;
    },

    mergeArrays: (...arrs) => {
        const mergedArr = [];
        for (let i = 0; i < arrs.length; i++) {
            if(stringUtils.isNotEmpty(arrs)) mergedArr.push(...arrs[i]);
        }
        return mergedArr;
    },

    partitionArray: (arr, predicate) => {
        if (stringUtils.isEmpty(arr)) return [[], []];
        return arr.reduce((acc, curr) => {
            acc[predicate(curr) ? 0 : 1].push(curr);
            return acc;
        }, [[], []]);
    }
}
