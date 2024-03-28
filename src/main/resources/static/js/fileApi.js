const fileApi = {
    async uploadFile(files, refPath, refId) {
        try {
            await ajax.file(`/file/${refPath}/${refId}`, files);
        } catch(ex) {
            console.error(ex);
        }
    },

    async deleteFile(fileId) {
        try {
            await ajax.delete(`/file/${fileId}`, "DELETE");
        } catch(ex) {
            console.error(ex);
        }
    },

    async deleteFiles(refPath, refId) {
        try {
            await ajax.delete(`/file/ref/${refPath}/${refId}`, "DELETE");
        } catch(ex) {
            console.error(ex);
        }
    }
}
