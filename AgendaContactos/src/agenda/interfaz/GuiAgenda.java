package agenda.interfaz;

import java.io.IOException;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private TextField txtBuscar;

	private Button btnClear;
	private Button btnSalir;

	@Override
	public void start(Stage stage) {
		agenda = new AgendaContactos(); // el modelo

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 1100, 700);
		stage.setScene(scene);
		stage.setTitle("Agenda de contactos");
		scene.getStylesheets().add(getClass().getResource("/darktheme.css").toExternalForm());
		stage.show();

	}

	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		panel.getStyleClass().add("fondo");
		return panel;
	}

	private BorderPane crearPanelPrincipal() {
		BorderPane panel = new BorderPane();
		panel.setPadding(new Insets(10));
		panel.setTop(crearPanelLetras());

		areaTexto = new TextArea();
		areaTexto.getStyleClass().add("textarea");
		panel.setCenter(areaTexto);

		panel.setLeft(crearPanelBotones());
		return panel;
	}

	private VBox crearPanelBotones() {
		VBox panel = new VBox();
		return panel;
	}

	private GridPane crearPanelLetras() {
		GridPane panel = new GridPane();
		char[] letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".toCharArray();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				char c = letras[i * 9 + j];
				Button btn = new Button(String.valueOf(c));
				btn.setOnAction(event -> contactosEnLetra(c));
				btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				btn.getStyleClass().add("botonLetra");
				GridPane.setHgrow(btn, Priority.ALWAYS);
				panel.add(btn, j, i);
			}
		}
		panel.setPadding(new Insets(10));
		panel.setHgap(5);
		panel.setVgap(5);
		return panel;
	}

	private MenuBar crearBarraMenu() {
		MenuBar barra = new MenuBar();
		Menu menu1 = new Menu("_Archivo");
		Menu menu2 = new Menu("_Operaciones");
		Menu menu3 = new Menu("_Help");

		itemImportar = new MenuItem("Importar agenda");
		itemExportarPersonales = new MenuItem("Exportar Personales");
		itemSalir = new MenuItem("Salir");
		itemBuscar = new MenuItem("Buscar");
		itemFelicitar = new MenuItem("Felicitar");
		itemAbout = new MenuItem("About");

		itemImportar.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
		itemImportar.setOnAction(e -> importarAgenda());
		itemExportarPersonales.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
		itemExportarPersonales.setOnAction(e -> exportarPersonales());
		itemExportarPersonales.setDisable(true);
		itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		itemSalir.setOnAction(e -> salir());
		itemBuscar.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
		itemBuscar.setOnAction(e -> buscar());
		itemFelicitar.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		itemFelicitar.setOnAction(e -> felicitar());
		itemAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		itemAbout.setOnAction(e -> about());

		menu1.getItems().addAll(itemImportar, itemExportarPersonales, new SeparatorMenuItem(), itemSalir);
		menu2.getItems().addAll(itemBuscar, itemFelicitar);
		menu3.getItems().addAll(itemAbout);

		return barra;
	}

	private void importarAgenda() {

	}

	private void exportarPersonales() {
		TextInputDialog ventana = new TextInputDialog("Introduzca nombre");
		ventana.setContentText("Nombre para el fichero");
		ventana.showAndWait();
		try {
			AgendaIO.exportarPersonales(agenda, ventana.getResult());
			areaTexto.setText("Exportados datos personajes");
		} catch (IOException e) {
			areaTexto.setText("Error al exportar");
		}
	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		if (rbtListarTodo.isSelected()) {
			areaTexto.setText(agenda.toString());
		} else {
			areaTexto.setText(String.valueOf(agenda.totalContactos()));
		}
	}

	private void personalesOrdenadosPorFecha() {
		clear();
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		ChoiceDialog<Character> ventana = new ChoiceDialog<>();
		ventana.setContentText("Seleccionar letra");
		ventana.showAndWait();
		char c = ventana.getResult();
		StringBuilder sb = new StringBuilder(java.time.LocalDate.now() + "\n");
		agenda.personalesEnLetra(c).forEach(personal -> sb.append(personal.toString()));
		areaTexto.setText(sb.toString());
	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
	}

	private void felicitar() {
		clear();
		StringBuilder sb = new StringBuilder(java.time.LocalDate.now() + "\n");
		agenda.felicitar().forEach(personal -> sb.append(personal.toString()));
		areaTexto.setText(sb.toString());

	}

	private void buscar() {
		clear();
		// a completar
		cogerFoco();

	}

	private void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Agenda de Contactos");
		alert.setContentText("Mi agenda de contactos");
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		alert.showAndWait();
	}

	private void clear() {
		areaTexto.setText("");
	}

	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
