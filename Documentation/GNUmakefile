PDFLATEX=pdflatex

all: Cindy3D_doc.pdf

Cindy3D_doc.pdf:
	$(PDFLATEX) -interaction=nonstopmode -jobname=Cindy3D_doc main.tex
	$(PDFLATEX) -interaction=nonstopmode -jobname=Cindy3D_doc main.tex

clean:
	$(RM) Cindy3D_doc.*

.PHONY: all clean

.DELETE_ON_ERROR:
