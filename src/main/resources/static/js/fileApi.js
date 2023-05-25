const fileApi = {
   async uploadFile(files, refPath, refId) {
        let formData = new FormData();
        files.forEach(it => {
            formData.append("file", it);
        })

        await com.ajaxFile({
            type: "POST",
            url: `/file/${refPath}/${refId}`,
            data: formData
        }, function (res) {
            console.log(res);
        })
    },

    async deleteFile(fileId) {
        await com.ajax({
            type: "DELETE",
            url: `/file/${fileId}`,
        }, function (res) {
            console.log(res);
        })
    },

    async deleteFiles(refPath, refId) {
        await com.ajax({
            type: "DELETE",
            url: `/file/ref/${refPath}/${refId}`,
        }, function (res) {
            console.log(res);
        })
    }
}
