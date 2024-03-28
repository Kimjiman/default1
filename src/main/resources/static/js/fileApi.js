const fileApi = {
    baseUrl: '/file',
    async uploadFile(files, refPath, refId) {
        try {
            await ajaxApi.file(`${fileApi.baseUrl}/${refPath}/${refId}`, files);
        } catch(ex) {
            console.error(ex);
        }
    },

    async deleteFile(fileId) {
        try {
            await ajaxApi.delete(`${fileApi.baseUrl}/${fileId}`);
        } catch(ex) {
            console.error(ex);
        }
    },

    async deleteFiles(refPath, refId) {
        try {
            await ajaxApi.delete(`${fileApi.baseUrl}/ref/${refPath}/${refId}`);
        } catch(ex) {
            console.error(ex);
        }
    }
}
