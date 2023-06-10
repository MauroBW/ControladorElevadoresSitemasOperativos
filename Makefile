.PHONY: clean run

LOG_DIR := logs
LOG_FILES := $(wildcard $(LOG_DIR)/*.txt)

clean:
	@if [ -n "$(LOG_FILES)" ]; then \
		echo "Eliminando archivos de registro..."; \
		rm -f $(LOG_FILES); \
	fi

run: clean
	@echo "Ejecutando 'docker compose up'..."
	@docker compose up