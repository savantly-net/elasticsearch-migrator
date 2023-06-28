SELF_DIR := $(realpath $(dir $(lastword $(MAKEFILE_LIST))))
export PROJECT_ROOT=$(SELF_DIR)

# Service information
#----------------------------------------------------------------------
SERVICE_NAME=elasticsearch-migrator

# Build information
#----------------------------------------------------------------------
REPO:=savantly
COMMIT_SHA:=$(shell git rev-parse --short=9 HEAD)
BRANCH_NAME:=$(shell git rev-parse --abbrev-ref HEAD | tr '/' '-')
IMAGE_URL:=${REPO}/${SERVICE_NAME}

VERSION:=$(shell awk '/^version/{print $$NF}' ./build.gradle)

IMAGE?=${IMAGE_URL}:${COMMIT_SHA}
IMAGE_WITH_VERSION?=${IMAGE_URL}:${VERSION}
IMAGE_WITH_COMMIT=${IMAGE}
IMAGE_WITH_BRANCH:=${IMAGE_URL}:${BRANCH_NAME}
IMAGE_WITH_LATEST:=${IMAGE_URL}:latest

TEST_IMAGE:=gradle:7


.DEFAULT_TARGET: docker-build
.PHONY: docker-build
docker-build:
	docker buildx build \
		--load \
		--platform=linux/amd64 \
       	-t ${IMAGE_WITH_VERSION} \
       	-t ${IMAGE_WITH_COMMIT} \
        -t ${IMAGE_WITH_BRANCH} \
        -t ${IMAGE_WITH_LATEST} \
		-f Dockerfile.ci .

.PHONY: docker-tag-check
docker-tag-check:
	echo ${IMAGE_WITH_VERSION}
	echo ${IMAGE_WITH_COMMIT}
	echo ${IMAGE_WITH_BRANCH}
	echo ${IMAGE_WITH_LATEST}

.PHONY: build-image
build-image:
	./gradlew build
	docker buildx build \
		--load \
		--platform=linux/amd64 \
       	-t ${IMAGE_WITH_VERSION} \
       	-t ${IMAGE_WITH_COMMIT} \
        -t ${IMAGE_WITH_BRANCH} \
        -t ${IMAGE_WITH_LATEST} \
		-f Dockerfile .

.PHONY: clean
clean:
	./gradlew clean

.PHONY: local-unit-test
local-unit-test:
	cd service && \
	./gradlew test

.PHONY: docker-clean-up-volumes
docker-clean-up-volumes:
	@docker compose down --volumes

.PHONY: run
run:
	./gradlew bootRun -x test

.PHONY: docker-compose
docker-compose:
	@docker compose -f docker-compose.dev.yml up

.PHONY: push
push:
	@docker push ${IMAGE_WITH_COMMIT}
	@docker push ${IMAGE_WITH_BRANCH}
	@docker push ${IMAGE_WITH_LATEST}