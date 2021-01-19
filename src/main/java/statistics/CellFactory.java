package statistics;

import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CellFactory extends ListCell<MovieStatistic> {
    @Override
    protected void updateItem(MovieStatistic item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (item != null) {
            TextFlow flow = new TextFlow();

            Text text1 = new Text("Movie: ");
            text1.setStyle("-fx-font-weight: bold");

            Text text2 = new Text(item.getMovie().getTitle() + "\n");

            Text text3, text4;
            switch (item.getKind()) {
                case EARNINGS:
                    text3 = new Text("Sum of bought tickets: ");
                    double numberOfTickets = (double) item.getAggregateValue().getValue();
                    text4 = new Text(String.valueOf(numberOfTickets));
                    break;
                case POPULARITY:
                    text3 = new Text("Number of bought tickets: ");
                    int sumOfPrices = (int) item.getAggregateValue().getValue();
                    text4 = new Text(String.valueOf(sumOfPrices));
                    break;
                default:
                    text3 = new Text();
                    text4 = new Text();
                    break;
            }
            text3.setStyle("-fx-font-weight: bold");
            text4.setStyle("-fx-font-col: red");
            flow.getChildren().addAll(text1, text2, text3, text4);
            setGraphic(flow);
        } else {
            setGraphic(null);
        }
    }
}
