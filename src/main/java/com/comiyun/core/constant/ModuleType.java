package com.comiyun.core.constant;

/**
 * 系统模块分类
 *
 * @author ydwcn
 * @ClassName: ModuleType
 * @date 2014-6-30 下午2:25:53
 */
public enum ModuleType {

    SYSTEM(0, "系统管理"),
    PERSON(1, "人事管理"),
    ATTENDANCE(2, "考勤管理"),
    PAYMENT(3, "薪酬管理"),
    ACHIEVEMENT(4, "绩效管理");

    /**
     * 模块ID
     */
    private int modulelId;
    /**
     * 模块名字
     */
    private String moduleName;

    private ModuleType(int modelId, String moduleName) {
        this.modulelId = modelId;
        this.moduleName = moduleName;
    }

    public int getModuleId() {
        return modulelId;
    }

    public String getModuleName() {
        return moduleName;
    }

}
