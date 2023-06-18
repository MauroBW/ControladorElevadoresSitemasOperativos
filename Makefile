.PHONY: clean run

LOG_DIR := logs
LOG_FILES := $(wildcard $(LOG_DIR)/*.txt)

clean:
	@mkdir -p $(LOG_DIR);
	@mkdir -p $(LOG_DIR)/info;
	@echo "Eliminando archivos";
	@if [ -n "$(LOG_FILES)" ]; then \
		mkdir -p $(LOG_DIR)/old ; \
		mv $(LOG_FILES) $(LOG_DIR)/old ; \
	fi

run: clean
	@echo "Ejecutando 'Simnulacion'..."
	@docker compose up