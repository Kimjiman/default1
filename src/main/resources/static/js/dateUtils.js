const datePattern = {
    DATE_TIME_FORMAT: "yyyy-MM-dd HH:mm:ss",
    DATE_FORMAT: "yyyy-MM-dd",
    TIME_FORMAT: "HH:mm:ss"
}

/**
 * 주어진 날짜를 pattern 형식으로 변경한다.
 * @param date
 * @param pattern
 * @returns {*}
 */
const formatDate = (date, pattern) => {
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
};

const getDateTimeString = (date) => {
    return formatDate(date, datePattern.DATE_TIME_FORMAT);
}

const getDateString = (date) => {
    return formatDate(date, datePattern.DATE_FORMAT);
}

const getTimeString = (date) => {
    return formatDate(date, datePattern.TIME_FORMAT);
}

const getDayOfWeekNumber = (date) => {
    let newDate = new Date(date);
    return newDate.getDay();
}

const getDayOfWeekString = (date) => {
    const dayOfWeek = {
        0: "월",
        1: "화",
        2: "수",
        3: "목",
        4: "금",
        5: "토",
        6: "일"
    }
    return dayOfWeek[getDayOfWeekNumber(date)];
}

const plusYears = (date, years) => {
    const newDate = new Date(date);
    newDate.setFullYear(date.getFullYear() + years);
    return newDate;
}

const minusYears = (date, years) => {
    const newDate = new Date(date);
    newDate.setFullYear(date.getFullYear() - years);
    return newDate;
}

const plusMonths = (date, months) => {
    const newDate = new Date(date);
    newDate.setMonth(date.getMonth() + months);
    return newDate;
}

const minusMonths = (date, months) => {
    const newDate = new Date(date);
    newDate.setMonth(date.getMonth() - months);
    return newDate;
}

const plusDays = (date, days) => {
    const newDate = new Date(date);
    newDate.setDate(date.getDate() + days);
    return newDate;
}

const minusDays = (date, days) => {
    const newDate = new Date(date);
    newDate.setDate(date.getDate() - days);
    return newDate;
}

const plusHours = (date, hours) => {
    const newDate = new Date(date);
    newDate.setHours(date.getHours() + hours);
    return newDate;
}

const minusHours = (date, hours) => {
    const newDate = new Date(date);
    newDate.setHours(date.getHours() - hours);
    return newDate;
}

const plusMinutes = (date, minutes) => {
    const newDate = new Date(date);
    newDate.setMinutes(date.getMinutes() + minutes);
    return newDate;
}

const minusMinutes = (date, minutes) => {
    const newDate = new Date(date);
    newDate.setMinutes(date.getMinutes() - minutes);
    return newDate;
}

const plusSeconds = (date, seconds) => {
    const newDate = new Date(date);
    newDate.setSeconds(date.getSeconds() + seconds);
    return newDate;
}

const minusSeconds = (date, seconds) => {
    const newDate = new Date(date);
    newDate.setSeconds(date.getSeconds() - seconds);
    return newDate;
}