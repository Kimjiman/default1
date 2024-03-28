/**
 * val가 empty 일때
 * @param val
 * @returns {boolean}
 */
const isEmpty = (val) => {
    if (typeof val === 'string') {
        return !val || val === "";
    } else {
        return !val;
    }
}

/**
 * val가 empty 아닐때
 * @param val
 * @returns {boolean}
 */
const isNotEmpty = (val) => {
    return !isEmpty(val);
}

/**
 * val가 empty일때 replaceVal로 대체
 * @param val
 * @param replaceVal
 * @returns {*}
 */
const ifEmpty = (val, replaceVal) => {
    return isEmpty(val) ? replaceVal : val;
}

/**
 * val가 blank 일때
 * @param val
 * @returns {boolean}
 */
const isBlank = (val) => {
    if (typeof val === 'string') {
        return !val || val.trim() === "";
    } else {
        return !val;
    }
}

/**
 * val가 blank가 아닐때
 * @param val
 * @returns {boolean}
 */
const isNotBlank = (val) => {
    return !isBlank(val);
}

/**
 * val가 blank일때 replaceVal로 대체
 * @param val
 * @param replaceVal
 * @returns {*}
 */
const ifBlank = (val, replaceVal) => {
    return isBlank(val) ? replaceVal : val;
}

/**
 * json을 queryString으로 변환
 * @param json
 * @returns {URLSearchParams}
 */
const jsonToQueryString = (json) => {
    return new URLSearchParams(json);
}

/**
 * queryString을 json으로 변환
 * @param queryString
 * @returns {{}}
 */
const queryStringToJson = (queryString) => {
    let json = {};
    queryString.split('&').forEach(pair => {
        let [key, value] = pair.split('=');
        json[decodeURIComponent(key)] = decodeURIComponent(value || '');
    });
    return json;
}

/**
 * 정규식
 * @param val
 * @param regex
 * @returns {boolean}
 */
const isRegex = (val, regex) => {
    if(isBlank(val)) return false;
    return regex.test(val);
}

/**
 * 영어소문자, 숫자, -_로 아이디 만들기
 * @param val
 * @returns {boolean}
 */
const isLoginId = (val) => {
    const regex = /^[a-z\-_.0-9]{6,20}$/;
    return isRegex(val, regex);
}

/**
 * 대소문자, 특수문자, 숫자로 비밀번호
 * @param val
 * @returns {boolean}
 */
const isPassword = (val) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    return isRegex(val, regex);
}

/**
 * 영문대소문자,한글완성형,특수문자허용 3~8자
 * @param val
 * @returns {boolean}
 */
const isName = (val) => {
    const regex = /^[a-zA-Z가-힣?=.*\[~!@#$%^&()\-_+\]]{3,8}$/;
    return isRegex(val, regex);
}

/**
 * 이메일
 * @param val
 * @returns {boolean}
 */
const isEmail = (val) => {
    const regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,}$/i;
    return isRegex(val, regex);
}

/**
 * 휴대폰 번호 숫자만 10~11자
 * @param val
 * @returns {boolean}
 */
const isMobile = (val) => {
    const regex = /^[0-9]{10,11}$/;
    return isRegex(val, regex);
}

/**
 * 숫자체크
 * @param val
 * @returns {boolean}
 */
const isNumber = (val) => {
    const regex = /^[0-9]+$/;
    return isRegex(val, regex);
}