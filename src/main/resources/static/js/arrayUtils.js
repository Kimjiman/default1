const removeElementByValue = (arr, val) => {
    if(isEmpty(arr) || isEmpty(val)) return arr;
    const newArr = Object.assign([], arr);
    const index = newArr.indexOf(val);
    if (index !== -1) {
        newArr.splice(index, 1);
    }
    return newArr;
};

const removeElementByIndex = (arr, index) => {
    if (isEmpty(arr) || index < 0 || index >= arr.length) return arr;
    const newArr = Object.assign([], arr);
    newArr.splice(index, 1);
    return newArr;
};

const mergeArrays = (...arrs) => {
    const mergedArr = [];
    for (let i = 0; i < arrs.length; i++) {
        if(isNotEmpty(arrs)) mergedArr.push(...arrs[i]);
    }
    return mergedArr;
};

const removeDuplicates = (arr) => {
    if (isEmpty(arr)) return arr;

    const type = typeof arr[0];
    const distinctArr = [];
    const set = new Set();

    if (type === 'object') {
        arr.forEach(it => {
            const stringifiedItem = JSON.stringify(it);
            if (!set.has(stringifiedItem)) {
                set.add(stringifiedItem);
                distinctArr.push(JSON.parse(stringifiedItem));
            }
        });
    } else {
        arr.forEach(it => {
            if (!set.has(it)) {
                set.add(it);
                distinctArr.push(it);
            }
        });
    }

    return distinctArr;
};

const compareAndRemoveDuplicates = (arr1, arr2) => {
    if (isEmpty(arr1) || isEmpty(arr2)) return arr1;

    const type1 = typeof arr1[0];
    const type2 = typeof arr2[0];

    if(type1 !== type2) {
        throw new Error(`Type mismatch: arr1: ${type1} !== arr2: ${type2}`);
    }

    const uniqueArray = [];
    const set = new Set();

    if (type1 === 'object') {
        arr2.forEach(it => set.add(JSON.stringify(it)));
        arr1.forEach(it => {
            if (!set.has(JSON.stringify(it))) {
                uniqueArray.push(it);
            }
        });
    } else {
        arr2.forEach(it => set.add(it));
        arr1.forEach(it => {
            if (!set.has(it)) {
                uniqueArray.push(it);
            }
        });
    }
    return uniqueArray;
}

const partitionArray = (arr, predicate) => {
    if (isEmpty(arr)) return [[], []];
    return arr.reduce((acc, curr) => {
        acc[predicate(curr) ? 0 : 1].push(curr);
        return acc;
    }, [[], []]);
};