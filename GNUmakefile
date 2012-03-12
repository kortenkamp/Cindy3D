ANT=ant

all: build
	$(MAKE) -C Reference all
	$(MAKE) -C Documentation all

build:
	cd Cindy3D && $(ANT) build

clean:
	$(MAKE) -C Reference clean
	$(MAKE) -C Documentation clean

.PHONY: all build clean
