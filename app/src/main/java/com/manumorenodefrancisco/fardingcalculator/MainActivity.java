package com.manumorenodefrancisco.fardingcalculator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView solutionTv, resultTv;
    private String operador = "";
    private float primerNumero = 0f;
    private boolean esperandoSegundoNumero = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        solutionTv = findViewById(R.id.solution_tv);
        resultTv = findViewById(R.id.result_tv);

        int[] idsNumeros = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6,
                R.id.button_7, R.id.button_8, R.id.button_9,
                R.id.button_punto
        };

        for (int id : idsNumeros) {
            Button b = findViewById(id);
            b.setOnClickListener(v -> escribirNumero(b.getText().toString()));
        }

        findViewById(R.id.button_sumar).setOnClickListener(v -> seleccionarOperador("+"));
        findViewById(R.id.button_restar).setOnClickListener(v -> seleccionarOperador("-"));
        findViewById(R.id.button_multiplicar).setOnClickListener(v -> seleccionarOperador("*"));
        findViewById(R.id.button_dividir).setOnClickListener(v -> seleccionarOperador("/"));

        findViewById(R.id.button_c).setOnClickListener(v -> solutionTv.setText(""));

        findViewById(R.id.button_ac).setOnClickListener(v -> {
            solutionTv.setText("");
            resultTv.setText("0");
            operador = "";
            primerNumero = 0f;
            esperandoSegundoNumero = false;
        });
        findViewById(R.id.button_porcentaje).setOnClickListener(v -> calcularPorcentaje());
        findViewById(R.id.button_equals).setOnClickListener(v -> calcular());

        findViewById(R.id.button_equals).setOnClickListener(v -> calcular());

    }

    private void escribirNumero(String num) {
        if (esperandoSegundoNumero) {
            solutionTv.setText("");
            esperandoSegundoNumero = false;
        }
        solutionTv.append(num);
    }

    private void seleccionarOperador(String op) {
        try {
            primerNumero = Float.parseFloat(solutionTv.getText().toString());
        } catch (Exception e) {
            primerNumero = 0f;
        }
        operador = op;
        esperandoSegundoNumero = true;
    }

    private void calcular() {
        if (operador.isEmpty()) return;

        float segundoNumero;
        try {
            segundoNumero = Float.parseFloat(solutionTv.getText().toString());
        } catch (Exception e) {
            segundoNumero = 0f;
        }

        float resultado = 0f;

        if (operador.equals("+")) {
            resultado = primerNumero + segundoNumero;
        } else if (operador.equals("-")) {
            resultado = primerNumero - segundoNumero;
        } else if (operador.equals("*")) {
            resultado = primerNumero * segundoNumero;
        } else if (operador.equals("/")) {
            if (segundoNumero == 0f) {
                resultTv.setText("Error");
                solutionTv.setText("");
                operador = "";
                return;
            }
            resultado = primerNumero / segundoNumero;
        }

        resultTv.setText(String.valueOf(resultado));
        solutionTv.setText(String.valueOf(resultado));
        operador = "";
        //esperandoSegundoNumero = true;
    }

    private void calcularPorcentaje() {
        if (solutionTv.getText().toString().isEmpty()) {
            return;
        }

        try {
            float numeroActual = Float.parseFloat(solutionTv.getText().toString());
            float resultadoPorcentaje = numeroActual / 100;

            solutionTv.setText(String.valueOf(resultadoPorcentaje));
            resultTv.setText(String.valueOf(resultadoPorcentaje));

        } catch (NumberFormatException e) {
            resultTv.setText("Error");
        }
    }

}
