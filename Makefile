.DEFAULT_GOAL := build

build:
	make -C app build

install:
	make -C app install

run-dist:
	make -C app run-dist

test:
	make -C app test

lint:
	make -C app lint

report:
	make -C app report

.PHONY: build test lint
