const arrSpliceByVal = (arr, val) => {
    if(isEmpty(arr) || isEmpty(val)) return arr;
    const index = arr.indexOf(val);
    if (index !== -1) {
        arr.splice(index, 1);
    }
    return arr;
};

const arrMerge = (...arr) => {
    const mergedArr = [];
    for (let i = 0; i < arr.length; i++) {
        if(isNotEmpty(arr)) mergedArr.push(...arr[i]);
    }
    return mergedArr;
};

const removeDuplicates = (arr) => {
    if(isEmpty(arr)) return arr;
    const type = typeof arr[0];
    if(type === 'object') {
        const convertStringArr = arr.map(it => JSON.stringify(it));
        const set = new Set(convertStringArr);
        const convertJsonSet = set.map(it => JSON.parse(it));
        return [...convertJsonSet];
    } else {
        return [...new Set(arr)];
    }
};