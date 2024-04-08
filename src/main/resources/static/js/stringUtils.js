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
        if(stringUtils/isEmpty(val) || stringUtils.isEmpty(regex)) return false;
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