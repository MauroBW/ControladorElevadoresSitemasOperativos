.PHONY: clean run

LOG_DIR := logs
LOG_FILES := $(wildcard $(LOG_DIR)/*.txt)

clean:
	@if [ -n "$(LOG_FILES)" ]; then \
		echo "Eliminando archivos de registro..."; \
		mkdir -p $(LOG_DIR)/old ; \
		mv $(LOG_FILES) $(LOG_DIR)/old ; \
	fi

run: clean
	@echo "Ejecutando 'docker compose up'..."
	@docker compose up