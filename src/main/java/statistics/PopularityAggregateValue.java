package statistics;

public class PopularityAggregateValue implements AggregateValue<Integer>{

    Integer value;

    public PopularityAggregateValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void addValue(Integer element) {
        value = value.intValue() + element.intValue();
    }
}
