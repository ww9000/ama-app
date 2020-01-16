package app.ww.ama.persistence;

public class QueryCriteria {

	private String fieldName;
	private QueryConditionType conditionType;
	private Object fieldValue;

	public enum QueryConditionType {
		EQUAL;
	}

	public QueryCriteria(String fieldName, QueryConditionType conditionType, Object fieldValue) {
		this.fieldName = fieldName;
		this.conditionType = conditionType;
		this.fieldValue = fieldValue;
	}

	public QueryConditionType getConditionType() {
		return conditionType;
	}

	public void setConditionType(QueryConditionType conditionType) {
		this.conditionType = conditionType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

}
