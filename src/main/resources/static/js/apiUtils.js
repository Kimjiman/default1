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
        const queryString = `?${new URLSearchParams(params).toString()}`;
        const options = {
            method: "DELETE",
            headers: headers,
        };
        return fetchApi.sendRequest(`${url}${queryString}`, options);
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