$(document).ready(function () {

})


/**
 * form안에 name을 기반으로 json 데이터를 생성한다.
 * @returns {null}
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
    self.value = self.value.replace(/[^0-9.]/g, '');
    self.value = self.value.replace(/(\..*)\./g, '$1');
    self.value = self.value.replace(/^(\d+)\.(\D*)$/, '$1');
}

/**
 * mobile형식으로 전화번호를 변경한다.
 * @param val
 * @returns {*|string}
 */
const inputFormatMobile = (val) => {
    if (isEmpty(val)) {
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