package com.boniu.starplan.entity;

public class VersionModel {


    /**
     * success : true
     * returnCode : 200
     * result : {"versionInfoVo":{"title":"更新提示","version":"1.0.0","content":"本次升级内容\n 1、优化产品性能，提升用户体验;","linkUrl":"","forceUp":false}}
     * timeOut : false
     */

    private boolean success;
    private String returnCode;
    private ResultBean result;
    private boolean timeOut;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    public static class ResultBean {
        /**
         * versionInfoVo : {"title":"更新提示","version":"1.0.0","content":"本次升级内容\n 1、优化产品性能，提升用户体验;","linkUrl":"","forceUp":false}
         */

        private VersionInfoVoBean versionInfoVo;

        public VersionInfoVoBean getVersionInfoVo() {
            return versionInfoVo;
        }

        public void setVersionInfoVo(VersionInfoVoBean versionInfoVo) {
            this.versionInfoVo = versionInfoVo;
        }

        public static class VersionInfoVoBean {
            /**
             * title : 更新提示
             * version : 1.0.0
             * content : 本次升级内容
             1、优化产品性能，提升用户体验;
             * linkUrl :
             * forceUp : false
             */

            private String title;
            private String version;
            private String content;
            private String linkUrl;
            private boolean forceUp;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public boolean isForceUp() {
                return forceUp;
            }

            public void setForceUp(boolean forceUp) {
                this.forceUp = forceUp;
            }
        }
    }
}
