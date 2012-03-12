RST2ANY=$(shell type -p rst2$(1) || type -p rst2$(1).py || echo '@echo rst2$(1) not found!; false')
RST2HTML=$(call RST2ANY,html)
RST2LATEX=$(call RST2ANY,latex)
PDFLATEX=pdflatex

all: CommandReference.pdf CommandReference.html

CommandReference.html: CommandReference.txt docutils.conf
	$(RST2HTML) $< $@

CommandReference.tex: CommandReference.txt docutils.conf
	$(RST2LATEX) $< $@

CommandReference.pdf: CommandReference.tex
	$(PDFLATEX) -interaction=nonstopmode $<
	$(PDFLATEX) -interaction=nonstopmode $<

clean:
	$(RM) $(addprefix CommandReference.,html tex pdf aux log out)

.PHONY: all clean

.DELETE_ON_ERROR:
