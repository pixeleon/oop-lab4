package net.pixeleon.khpi.oop.labfour.prfactapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.omg.CORBA.Environment;

public class PrimeFactorsController {
    public TextField textFieldFrom;
    public TextField textFieldTo;
    public TextArea textAreaFactors;
    public Button buttonStart;
    public Button buttonSuspend;
    public Button buttonResume;
    public Button buttonFinish;
    public ProgressBar progressBar;

    PrimeFactorsThread primeFactorsThread = new PrimeFactorsThread(
            this::displayCurrentNumberFunc,
            this::displayFactorFunc,
            this::newLineFunc,
            this::percentageFunc,
            this::finishFunc);

    private void newLineFunc() {
        textAreaFactors.appendText(System.lineSeparator());
    }

    private void finishFunc() {
        buttonStart.setDisable(false);
        buttonSuspend.setDisable(true);
        buttonResume.setDisable(true);
        buttonFinish.setDisable(true);
    }

    private void percentageFunc() {
        progressBar.setProgress(primeFactorsThread.getPercentage());
    }

    private void displayFactorFunc() {
        textAreaFactors.appendText(primeFactorsThread.getLastFoundFactor() + "\t\t");
    }

    private void displayCurrentNumberFunc() {
        textAreaFactors.appendText(primeFactorsThread.getCurrentNumber()+ ": ");
    }

    private void showNumberFormatError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid data");
        alert.setContentText("Non integer input provided. Please, check your data.");
        alert.showAndWait();
    }

    public void doStart(ActionEvent actionEvent) {
        try {
            primeFactorsThread.setFrom(Integer.parseInt(textFieldFrom.getText()));
            primeFactorsThread.setTo(Integer.parseInt(textFieldTo.getText()));
            textAreaFactors.setText("");
            progressBar.setProgress(0.0);
            buttonStart.setDisable(true);
            buttonSuspend.setDisable(false);
            buttonResume.setDisable(true);
            buttonFinish.setDisable(false);
            primeFactorsThread.start();
        } catch (NumberFormatException e) {
            showNumberFormatError();
        }
    }

    public void doSuspend(ActionEvent actionEvent) {
        primeFactorsThread.suspend();
        buttonStart.setDisable(true);
        buttonSuspend.setDisable(true);
        buttonResume.setDisable(false);
        buttonFinish.setDisable(false);
    }

    public void doResume(ActionEvent actionEvent) {
        primeFactorsThread.resume();
        buttonStart.setDisable(true);
        buttonSuspend.setDisable(false);
        buttonResume.setDisable(true);
        buttonFinish.setDisable(false);
    }

    public void doFinish(ActionEvent actionEvent) {
        primeFactorsThread.finish();
    }
}
