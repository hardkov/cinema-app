package statistics;

public class EarningsAggregateValue implements AggregateValue<Double>{

    Double value;

    public EarningsAggregateValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void addValue(Double element) {
        value = value.doubleValue() + element.doubleValue();
    }
}
