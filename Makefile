version=1.0.0
name=larsq/moviedb

.PHONY: help
.DEFAULT_GOAL := help

help: ## prints this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

clean: ## clean up previous build
	gradle clean

build: clean ## build jar-file
	gradle -Pversion=${version} build

run: build ## run application locally
	java -jar build/libs/moviedb-${version}.jar

release: build ## build docker file
	docker build --build-arg VERSION=${version} -t larsq/moviedb:${version} -t larsq/moviedb:latest .

docker-run: ## run docker file locally
	docker run --name moviedb --rm -p 8080:8080 larsq/moviedb:${version}

docker-run-shell: ## open shell in docker-image
	docker run -it --name moviedb --rm -p 8080:8080 --entrypoint sh  larsq/moviedb:${version}