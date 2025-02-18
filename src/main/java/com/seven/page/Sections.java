package com.seven.page;

public class Sections {
    public enum Section {
        USERS("user/index"),
        COURSES("course/index"),
        CATEGORIES("category/index"),
        GROUPS("group/index"),
        BRANCHES("branch/index"),
        EVENTS_ENGINE("eventsengine/notification_index"),
        USER_TYPES("acl/index"),
        IMPORT_EXPORT("import/index"),
        REPORTS("reports/index"),
        ACCOUNT_SETTINGS("basic_index");

        private final String path;

        Section(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
