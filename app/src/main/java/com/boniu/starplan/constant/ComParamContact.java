package com.boniu.starplan.constant;

public class ComParamContact {

    public final static class Common {
        public final static String APPID = "appId";
        public final static String ACCESSTOKEN = "accessToken";
        public final static String TIMESTAMP = "timestamp";
        public final static String REFRESH_TOKEN = "refreshToken";
        public final static String SIGN = "sign";
        public final static String TOKEN_KEY = "token";
        public final static String IS_FIRST = "isFirst";

    }

    public final static class Code {
        //http请求成功状态码
        public final static int HTTP_SUCESS = 0;
        //AccessToken错误或已过期
        public static final int ACCESS_TOKEN_EXPIRED = 100010101;
        //RefreshToken错误或已过期
        public static final int REFRESH_TOKEN_EXPIRED = 100010102;
        //帐号在其它手机已登录
        public static final int OTHER_PHONE_LOGINED = 100021006;
        //timestamp过期
        public static final int TIMESTAMP_ERROR = 100010104;
        //缺少授权信息,没有accessToken,应该是没有登录
        public final static int NO_ACCESS_TOKEN = 100010100;
        //签名错误
        public final static int ERROR_SIGN = 100010105;
        //设备未绑定
        public final static int DEVICE_NO_BIND = 100022001;

    }

    public final static class Token {
        public final static String PATH = "";
        public final static String AUTH_MODEL = "authModel";
    }

    public final static class DownLoad {
        public final static String PATH = "down";
       // public final static String AUTH_MODEL = "authModel";
    }
    public final static class Login {
        public final static String LOGIN = "api/user/login";
        public final static String GET_CODE = "api/user/sendMsg";
        public final static String MOBILE = "mobile";
        public final static String CODE = "verificationCode";
    }

    public final static class Main {
        //修改用户信息
        public final static String UPDATE_USER_INFO = "api/user/updateUserInfo";
        public final static String Head_PHOTO = "headphoto";
        //上传用户信息
        public final static String UPDATE_LOAD_HEAD_PHOTO = "api/user/uploadHeadPhoto";
        //查询用户信息
        public final static String getUserInfo = "api/user/getUserInfo";
        public final static String isNewUserAndGetGoldWithoutToken = "api/sign/isNewUserAndGetGoldWithoutToken";

        /**
         * 签到模块
         */
        //是否签到
        public final static String IS_SIGN = "api/sign/isSign";
        //获取周联系签到的天数
        public final static String getSignAmount = "api/sign/getSignAmount";
        //获取累计签到奖励
        public final static String getCumulativeSignRewards = "api/sign/getCumulativeSignRewards";

        public final static String isGetCumulativeSignRewards = "api/sign/isGetCumulativeSignRewards";
        //签到
        public final static String GET_SIGN = "api/sign/signIn";
        /**
         * 任务模块
         */
        //新手任务 && 每日必做
        public final static String queryTaskMarketList = "api/taskMarketConfig/queryTaskMarketList";
        //任务列表
        public final static String TASk_LIST = "api/task/list";
        //领取任务
        public final static String TASK_APPLY = "api/task/user/apply";
        //开始任务
        public final static String TASK_BEGIN = "api/task/user/begin";
        public final static String giveUp = "api/task/user/giveUp";
        //任务完成 领金币
        public final static String TASk_END = "api/task/user/end";
        //获取用户任务详情
        public final static String GET_TASK = "api/task/user/getTask";
        //用户进行中的任务
        public final static String List_to_Do = "api/task/user/listToDo";
        //任务翻倍
        public final static String addPrepositionDouble = "api/task/user/addPrepositionDouble";
        public final static String queryEveryoneDoTask = "api/taskMarketConfig/queryEveryoneDoTask";
        public final static String repetition = "api/task/user/tryTaskKeepLiveDownload";

        /**
         * 宝箱相关
         */
        //签到查询宝箱 状态
        public final static String getTreasureBox = "api/treasureBoxTask/getTreasureBox";
        public final static String queryTreasureBoxTaskStatus = "api/treasureBoxTask/queryTreasureBoxTaskStatus";
        public final static String getDoubleTreasureBox = "api/treasureBoxTask/getDoubleTreasureBox";

        /**
         * 首页时间检测
         */
        //时间检查的
        public final static String checkCollectTime = "api/goldCoinCollect/checkCollectTime";
        public final static String getGoldCoin = "api/goldCoinCollect/getGoldCoin";
        public final static String getCollectTaskRecord = "api/goldCoinCollect/collectTaskRecord";
        /**
         * 上传 审核资料
         */
        public final static String uploadAudit = "api/task/user/uploadAudit";
        public final static String submitAudit = "api/task/user/submitAudit";

        /**
         * 用户金币详情
         */
        public final static String account = "api/gold/account";
        //收益明细
        public final static String earning = "api/gold/earning/list";
        //支出明细
        public final static String expend = "api/gold/expend/list";
        /**
         * 提现
         */
        //普通提现列表
        public final static String ordinaryList = "api/gold/expend/ordinary/list";
        //专属提现列表
        public final static String exclusiveList = "api/gold/expend/exclusive/list";
        //特惠任务完成情况
        public final static String preferential = "api/gold/expend/preferential";
        //金额提现
        public final static String goldLaunch = "api/gold/launch";
        //获取用户转账用户详情
        public final static String transferInfo = "api/gold/expend/transfer/info";
        //提现记录
        public final static String withdrawalList = "api/gold/withdrawal/list";
        public final static String listAuditSchedule = "api/task/user/listAuditSchedule";
        // 领取激励视频任务
        public final static String addVideoAD = "api/task/user/addVideoAD";
        //游戏
        public final  static  String  gameUrl="api/game/getUrl";
        public final  static  String  sendMsg="api/user/sendMsg";
    }
}













