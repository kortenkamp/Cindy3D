ANT=ant

all: build Reference/all Documentation/all

build:
	cd Cindy3D && $(ANT) bundles

clean:
	$(MAKE) -C Reference clean
	$(MAKE) -C Documentation clean

Reference/all Documentation/all Reference/documented_commands.txt:
	$(MAKE) -C $(@D) $(@F)

check: Reference/documented_commands.txt
	grep @CindyScript \
	Cindy3D/src/de/tum/in/cindy3dplugin/Cindy3DPlugin.java \
	| cut -d\" -f2 | sort | diff -U0 $< -

.PHONY: all build clean check
.PHONY: Reference/all Documentation/all
