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
        mergedArr.push(...arr[i]);
    }
    return mergedArr;
};

const removeDuplicates = (arr) => {
    if(isEmpty(arr)) return arr;
    const type = typeof arr[0];
    if(type === 'object') {
        const convertJsonArr = arr.map(it => JSON.stringify(it));
        const convertSet = new Set(convertJsonArr);
        const distinctArr = convertSet.map(it => JSON.parse(it));
        return [...distinctArr];
    } else {
        return [...new Set(arr)];
    }
};