package agenda.interfaz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
		scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
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
		areaTexto.setEditable(false);
		panel.setCenter(areaTexto);

		panel.setLeft(crearPanelBotones());
		return panel;
	}

	private VBox crearPanelBotones() {
		VBox panel = new VBox();

		panel.setPadding(new Insets(10));
		panel.setSpacing(10);

		txtBuscar = new TextField();
		txtBuscar.setMinWidth(40);
		txtBuscar.setOnAction(e -> buscar());
		txtBuscar.setPromptText("Buscar");
		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));

		rbtListarTodo = new RadioButton("Listar toda la agenda");
		rbtListarTodo.setSelected(true);

		rbtListarSoloNumero = new RadioButton("Listar nº de contactos");

		ToggleGroup grupo = new ToggleGroup();
		rbtListarSoloNumero.setToggleGroup(grupo);
		rbtListarTodo.setToggleGroup(grupo);

		btnListar = new Button("Listar");
		btnListar.getStyleClass().add("botones");
		btnListar.setPrefWidth(250);
		btnListar.setOnAction(e -> listar());
		VBox.setMargin(btnListar, new Insets(0, 0, 40, 0));

		btnPersonalesEnLetra = new Button("Contactos personales en letra");
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setPrefWidth(250);
		btnPersonalesEnLetra.setOnAction(e -> contactosPersonalesEnLetra());

		btnPersonalesOrdenadosPorFecha = new Button("Contactos personales ordenados por fecha");
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setPrefWidth(250);
		btnPersonalesOrdenadosPorFecha.setOnAction(e -> personalesOrdenadosPorFecha());

		btnClear = new Button("Clear");
		btnClear.getStyleClass().add("botones");
		btnClear.setPrefWidth(250);
		btnClear.setOnAction(e -> clear());
		VBox.setMargin(btnClear, new Insets(40, 0, 0, 0));

		btnSalir = new Button("Salir");
		btnSalir.getStyleClass().add("botones");
		btnSalir.setPrefWidth(250);
		btnSalir.setOnAction(e -> salir());

		panel.getChildren().addAll(txtBuscar, rbtListarTodo, rbtListarSoloNumero, btnListar, btnPersonalesEnLetra,
				btnPersonalesOrdenadosPorFecha, btnClear, btnSalir);

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

		barra.getMenus().addAll(menu1, menu2, menu3);

		return barra;
	}

	private void importarAgenda() {

		FileChooser selector = new FileChooser();
		selector.setTitle("Abrir fichero de datos");
		selector.setInitialDirectory(new File("."));
		selector.getExtensionFilters().addAll(new ExtensionFilter("csv", "*.csv"));
		File f = selector.showOpenDialog(null);
		if (f != null) {
			areaTexto.setText("Nº de lineas erroneas: " + AgendaIO.importar(agenda, f.getName()));
		}
		itemImportar.setDisable(true);
		itemExportarPersonales.setDisable(false);
	}

	private void exportarPersonales() {
		FileChooser ventana = new FileChooser();
		ventana.setTitle("Guardar en");
		ventana.getExtensionFilters().add(new ExtensionFilter("Fichero de texto", "*.txt"));
		File file = ventana.showSaveDialog(null);
		try {
			AgendaIO.exportarPersonales(agenda, file.getName());
			areaTexto.setText("Exportados datos personajes");
		} catch (IOException | NullPointerException e) {
			areaTexto.setText("Error al exportar");
		}
	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		if (itemImportar.isDisable()) {
			if (rbtListarTodo.isSelected()) {
				areaTexto.setText(agenda.toString());
			} else {
				areaTexto.setText(String.valueOf(agenda.totalContactos()));
			}
		} else {
			areaTexto.setText("Importar agenda para realizar acción");
		}
	}

	private void personalesOrdenadosPorFecha() {
		clear();
		if (itemImportar.isDisable()) {
			ChoiceDialog<Character> ventana = new ChoiceDialog<>();
			ventana.setContentText("Seleccionar letra");
			for (char c : "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".toCharArray()) {
				ventana.getItems().add(c);
			}
			ventana.showAndWait();
			Character c = ventana.getSelectedItem();
			if (c == null) {
				areaTexto.setText("No se ha elegido letra");
			} else {
				try {
					agenda.personalesOrdenadosPorFechaNacimiento(c).forEach(personal -> areaTexto.appendText(personal.toString()));
				} catch (Exception e) {
					areaTexto.setText("No existe ningun contacto personal con esa letra");
				}
			}
		} else {
			areaTexto.setText("Importar agenda para realizar acción");
		}

	}

	private void contactosPersonalesEnLetra() {
		clear();
		if (itemImportar.isDisable()) {
			ChoiceDialog<Character> ventana = new ChoiceDialog<>();
			ventana.setContentText("Seleccionar letra");
			for (char c : "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".toCharArray()) {
				ventana.getItems().add(c);
			}
			ventana.showAndWait();
			char c = ventana.getResult();
			agenda.personalesEnLetra(c).forEach(personal -> areaTexto.appendText(personal.toString()));
		} else {
			areaTexto.setText("Importar agenda para realizar acción");
		}
	}

	private void contactosEnLetra(char letra) {
		clear();
		if (itemImportar.isDisable()) {
			if (agenda.totalContactos() == 0) {
				areaTexto.setText("Debes importar la agenda primero");
			} else {
				if (agenda.contactosEnLetra(letra) == null) {
					areaTexto.setText("No existe ningun contacto con esa letra");
				} else {
					areaTexto.setText(agenda.contactosEnLetra(letra).toString());
				}
			}
		} else {
			areaTexto.setText("Importar agenda para realizar acción");
		}
	}

	private void felicitar() {
		clear();
		if (itemImportar.isDisable()) {
			areaTexto.setText(java.time.LocalDate.now() + "\n");
			agenda.felicitar().forEach(personal -> areaTexto.appendText(personal.toString()));
		} else {
			areaTexto.setText("Importar agenda para realizar acción");
		}

	}

	private void buscar() {
		clear();
		if (itemImportar.isDisable()) {
			String txt = txtBuscar.getText();
			if (txt.isBlank()) {
				areaTexto.setText("Campo de texto Buscar vacio");
			} else {
				List<Contacto> list = agenda.buscarContactos(txt);
				if (list.isEmpty()) {
					areaTexto.setText("No existen contactos que contengan la secuencia de caracteres");
				} else {
					list.forEach(contacto -> areaTexto.appendText(contacto.toString()));
				}
			}
		} else {
			areaTexto.setText("Importar agenda para realizar acción");
		}
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
