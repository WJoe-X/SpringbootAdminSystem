package lib.sixzeroseven.admin.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PictureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PictureExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlIsNull() {
            addCriterion("origin_pic_url is null");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlIsNotNull() {
            addCriterion("origin_pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlEqualTo(String value) {
            addCriterion("origin_pic_url =", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlNotEqualTo(String value) {
            addCriterion("origin_pic_url <>", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlGreaterThan(String value) {
            addCriterion("origin_pic_url >", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("origin_pic_url >=", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlLessThan(String value) {
            addCriterion("origin_pic_url <", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlLessThanOrEqualTo(String value) {
            addCriterion("origin_pic_url <=", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlLike(String value) {
            addCriterion("origin_pic_url like", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlNotLike(String value) {
            addCriterion("origin_pic_url not like", value, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlIn(List<String> values) {
            addCriterion("origin_pic_url in", values, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlNotIn(List<String> values) {
            addCriterion("origin_pic_url not in", values, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlBetween(String value1, String value2) {
            addCriterion("origin_pic_url between", value1, value2, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andOriginPicUrlNotBetween(String value1, String value2) {
            addCriterion("origin_pic_url not between", value1, value2, "originPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlIsNull() {
            addCriterion("fixed_pic_url is null");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlIsNotNull() {
            addCriterion("fixed_pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlEqualTo(String value) {
            addCriterion("fixed_pic_url =", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlNotEqualTo(String value) {
            addCriterion("fixed_pic_url <>", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlGreaterThan(String value) {
            addCriterion("fixed_pic_url >", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("fixed_pic_url >=", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlLessThan(String value) {
            addCriterion("fixed_pic_url <", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlLessThanOrEqualTo(String value) {
            addCriterion("fixed_pic_url <=", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlLike(String value) {
            addCriterion("fixed_pic_url like", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlNotLike(String value) {
            addCriterion("fixed_pic_url not like", value, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlIn(List<String> values) {
            addCriterion("fixed_pic_url in", values, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlNotIn(List<String> values) {
            addCriterion("fixed_pic_url not in", values, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlBetween(String value1, String value2) {
            addCriterion("fixed_pic_url between", value1, value2, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andFixedPicUrlNotBetween(String value1, String value2) {
            addCriterion("fixed_pic_url not between", value1, value2, "fixedPicUrl");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNull() {
            addCriterion("update_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNotNull() {
            addCriterion("update_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtEqualTo(Date value) {
            addCriterion("update_at =", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotEqualTo(Date value) {
            addCriterion("update_at <>", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThan(Date value) {
            addCriterion("update_at >", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("update_at >=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThan(Date value) {
            addCriterion("update_at <", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThanOrEqualTo(Date value) {
            addCriterion("update_at <=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIn(List<Date> values) {
            addCriterion("update_at in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotIn(List<Date> values) {
            addCriterion("update_at not in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtBetween(Date value1, Date value2) {
            addCriterion("update_at between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotBetween(Date value1, Date value2) {
            addCriterion("update_at not between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUploaderNameIsNull() {
            addCriterion("uploader_name is null");
            return (Criteria) this;
        }

        public Criteria andUploaderNameIsNotNull() {
            addCriterion("uploader_name is not null");
            return (Criteria) this;
        }

        public Criteria andUploaderNameEqualTo(String value) {
            addCriterion("uploader_name =", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameNotEqualTo(String value) {
            addCriterion("uploader_name <>", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameGreaterThan(String value) {
            addCriterion("uploader_name >", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameGreaterThanOrEqualTo(String value) {
            addCriterion("uploader_name >=", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameLessThan(String value) {
            addCriterion("uploader_name <", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameLessThanOrEqualTo(String value) {
            addCriterion("uploader_name <=", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameLike(String value) {
            addCriterion("uploader_name like", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameNotLike(String value) {
            addCriterion("uploader_name not like", value, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameIn(List<String> values) {
            addCriterion("uploader_name in", values, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameNotIn(List<String> values) {
            addCriterion("uploader_name not in", values, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameBetween(String value1, String value2) {
            addCriterion("uploader_name between", value1, value2, "uploaderName");
            return (Criteria) this;
        }

        public Criteria andUploaderNameNotBetween(String value1, String value2) {
            addCriterion("uploader_name not between", value1, value2, "uploaderName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}