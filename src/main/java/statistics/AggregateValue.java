package statistics;

public interface AggregateValue<T> {
    T getValue();
    void addValue(T element);
}
