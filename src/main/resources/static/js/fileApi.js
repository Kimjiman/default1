const fileApi = {
   async uploadFile(files, division, refId) {
        let formData = new FormData();
        files.forEach(it => {
            formData.append("file", it);
        })

        await com.requestFileAjax({
            type: "POST",
            url: "/uploadFile/" + division + "/" + refId,
            data: formData
        }, function (res) {
            console.log(res);
        })
    },

    async deleteFile(fileId) {
        await com.ajax({
            type: "DELETE",
            url: "/deleteFile/" + fileId,
        }, function (res) {
            console.log(res);
        })
    },

    async deleteFiles(division, refId) {
        await com.ajax({
            type: "DELETE",
            url: "/deleteFileByRef/" + division + "/" + refId,
        }, function (res) {
            console.log(res);
        })
    }
}
