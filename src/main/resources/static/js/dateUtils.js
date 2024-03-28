/**
 * 날짜 변환
 * yyyy-MM-dd, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd a/p hh:mm:ss, yyyy-MM-dd E
 * @param {날짜 Date} format
 * @param date
 * @returns
 */
const getDate = (format, date) => {

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
};

const addDate = (date, interval, units) => {
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
};