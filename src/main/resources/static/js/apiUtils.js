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