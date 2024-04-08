package com.po2.jukebox;

import com.po2.jukebox.domain.Band;
import com.po2.jukebox.domain.Music;
import com.po2.jukebox.infra.BandDAO;
import com.po2.jukebox.infra.MusicDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Controller {

    private boolean editing;
    private final int REFRESH_TIME_IN_SECONDS = 1;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private TextField musicName;
    @FXML
    private TextField musicBandName;
    @FXML
    private TextField musicDuration;
    @FXML
    private TextField musicLink;
    @FXML
    private TextField musicAmount;

    @FXML
    private TableView<Music> musicsTable;
    @FXML
    private TableColumn<Music, Integer> musicIdColumn;
    @FXML
    private TableColumn<Music, String> musicNameColumn;
    @FXML
    private TableColumn<Music, String> musicBandNameColumn;
    @FXML
    private TableColumn<Music, Integer> musicDurationColumn;
    @FXML
    private TableColumn<Music, String> musicLinkColumn;
    @FXML
    private TableColumn<Music, Music> musicActionColumn;

    @FXML
    private TextField bandName;
    @FXML
    private TextField bandGender;
    @FXML
    private TextField bandCreationDate;
    @FXML
    private TextField bandAmount;

    @FXML
    private TableView<Band> bandsTable;
    @FXML
    private TableColumn<Band, String> bandNameColumn;
    @FXML
    private TableColumn<Band, LocalDate> bandCreationDateColumn;
    @FXML
    private TableColumn<Band, String> bandGenderColumn;
    @FXML
    private TableColumn<Band, Band> bandActionColumn;

    @FXML
    private void editMusicName(TableColumn.CellEditEvent<Music, String> t) {
        Thread editMusicNameThread = new Thread(() -> {
            try {
                Music music = t.getTableView().getItems().get(t.getTablePosition().getRow());
                music.setName(t.getNewValue());
                MusicDAO.update(music);
            } finally {
                editing = false;
            }
        });

        editMusicNameThread.start();
    }

    @FXML
    private void editMusicDuration(TableColumn.CellEditEvent<Music, Integer> t) {
        Thread editMusicDurationThread = new Thread(() -> {
            try {
                Music music = t.getTableView().getItems().get(t.getTablePosition().getRow());
                music.setDurationInSeconds(t.getNewValue());
                MusicDAO.update(music);
            } finally {
                editing = false;
            }
        });

        editMusicDurationThread.start();
    }

    @FXML
    private void editMusicLink(TableColumn.CellEditEvent<Music, String> t) {
        Thread editMusicLinkThread = new Thread(() -> {
            try {
                Music music = t.getTableView().getItems().get(t.getTablePosition().getRow());
                music.setLink(t.getNewValue());
                MusicDAO.update(music);
            } finally {
                editing = false;
            }
        });

        editMusicLinkThread.start();
    }

    @FXML
    private void editBandName(TableColumn.CellEditEvent<Band, String> t) {
        Thread editBandNameThread = new Thread(() -> {
            try {
                Band band = t.getTableView().getItems().get(t.getTablePosition().getRow());
                BandDAO.update(band, t.getNewValue());
            } finally {
                editing = false;
            }
        });

        editBandNameThread.start();
    }

    @FXML
    private void editBandCreationDate(TableColumn.CellEditEvent<Band, LocalDate> t) {
        Thread editBandCreationDateThread = new Thread(() -> {
            try {
                Band band = t.getTableView().getItems().get(t.getTablePosition().getRow());
                band.setCreationDate(t.getNewValue());
                BandDAO.update(band, null);
            } finally {
                editing = false;
            }
        });

        editBandCreationDateThread.start();
    }

    @FXML
    private void editBandGender(TableColumn.CellEditEvent<Band, String> t) {
        Thread editBandGenderThread = new Thread(() -> {
            try {
                Band band = t.getTableView().getItems().get(t.getTablePosition().getRow());
                band.setGender(t.getNewValue());
                BandDAO.update(band, null);
            } finally {
                editing = false;
            }
        });

        editBandGenderThread.start();
    }

    @FXML
    private void editStart() {
        editing = true;
    }

    @FXML
    private void editCancel() {
        editing = false;
    }

    @FXML
    private void initialize() {
        musicIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        musicNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        musicNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        musicBandNameColumn.setCellValueFactory(new PropertyValueFactory<>("bandName"));
        musicDurationColumn.setCellValueFactory(new PropertyValueFactory<>("durationInSeconds"));
        musicDurationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        musicLinkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
        musicLinkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        musicActionColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        musicActionColumn.setCellFactory(param -> new TableCell<Music, Music>() {
            private final Button deleteButton = new Button("Remover");

            @Override
            protected void updateItem(Music music, boolean empty) {
                super.updateItem(music, empty);

                if (music == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            Thread deleteMusicThread = new Thread(() -> {
                                MusicDAO.remove(music.getId());
                                musicsTable.getItems().remove(music);
                            });
                            deleteMusicThread.start();
                        }
                );
            }
        });

        Thread musicsRefreshThread = new Thread(() -> {
            while (true) {
                if (!editing) {
                    ArrayList<Music> updatedMusics = MusicDAO.list();
                    Platform.runLater(() -> musicsTable.getItems().setAll(updatedMusics));
                }
                try {
                    TimeUnit.SECONDS.sleep(REFRESH_TIME_IN_SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        musicsRefreshThread.setDaemon(true);

        musicsRefreshThread.start();

        bandNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bandNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bandCreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        bandCreationDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return DATE_FORMAT.format(date);
            }

            @Override
            public LocalDate fromString(String date) {
                return LocalDate.parse(date, DATE_FORMAT);
            }
        }));
        bandGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        bandGenderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bandActionColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        bandActionColumn.setCellFactory(param -> new TableCell<Band, Band>() {
            private final Button deleteButton = new Button("Remover");

            @Override
            protected void updateItem(Band band, boolean empty) {
                super.updateItem(band, empty);

                if (band == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            Thread deleteBandThread = new Thread(() -> {
                                BandDAO.remove(band.getName());
                                bandsTable.getItems().remove(band);
                            });
                            deleteBandThread.start();
                        }
                );
            }
        });

        Thread bandsRefreshThread = new Thread(() -> {
            while (true) {
                if (!editing) {
                    ArrayList<Band> updatedBands = BandDAO.list();
                    Platform.runLater(() -> bandsTable.getItems().setAll(updatedBands));
                }
                try {
                    TimeUnit.SECONDS.sleep(REFRESH_TIME_IN_SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        bandsRefreshThread.setDaemon(true);

        bandsRefreshThread.start();
    }

    @FXML
    private void handleAddMusic() {
        Thread addMusicThread = new Thread(() -> {
            Music musicToAdd = new Music();
            musicToAdd.setName(musicName.getText());
            musicToAdd.setBandName(musicBandName.getText());
            musicToAdd.setDurationInSeconds(Integer.parseInt(musicDuration.getText()));
            musicToAdd.setLink(musicLink.getText());

            for (int i = 0; i < Integer.parseInt(this.musicAmount.getText()); i++) {
                MusicDAO.insert(musicToAdd);
            }

            musicName.setText("");
            musicBandName.setText("");
            musicDuration.setText("");
            musicLink.setText("");
            musicAmount.setText("1");
        });

        addMusicThread.start();
    }

    @FXML
    private void handleAddBand() {
        Thread addBandThread = new Thread(() -> {
            Band bandToAdd = new Band();
            bandToAdd.setName(bandName.getText());
            bandToAdd.setGender(bandGender.getText());
            bandToAdd.setCreationDate(LocalDate.parse(bandCreationDate.getText(), DATE_FORMAT));

            int amount = Integer.parseInt(bandAmount.getText());
            String originalBandName = bandToAdd.getName();
            for (int i = 0; i < amount; i++) {
                if (amount > 1) {
                    bandToAdd.setName(originalBandName + (i + 1));
                }
                BandDAO.insert(bandToAdd);

            }

            bandName.setText("");
            bandGender.setText("");
            bandCreationDate.setText("");
            bandAmount.setText("1");
        });

        addBandThread.start();
    }
}
