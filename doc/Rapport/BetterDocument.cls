% =================================================================================================================
% Copyright 2019 Justine MARTIN
%
% Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
%
% The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
%
% THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
%
% =================================================================================================================
%
%
% Pour utiliser ce fichier : \documentclass[report]{BetterDocument}
%
% Ce fichiers ajoute les commandes suivantes :
%
%	\title : Change le titre du document
%	\subtitle : Change le sous titre du document
%	\author : Change les auteurs du document
%	\date : Change la date du document
%	\institute : Change le lieu de création du document (nom de l'école, entreprise, etc)
%	\instituteLogo : Change le logo de l'etablissement
%	\docLogo : Change le logo du document
%	\pageDeGarde : Construit la page de garde en fonction des informations ci dessus
%
%	\tableDesMatieres : Construit la page de table des matières
%	\tocCounter : Change la profondeur de la table des matières (va jusqu'aux subsection par défaut)
%
%	\japanese : Permet d'écrire des Kana et des Kanjis
%
%	\lstinputlisting[language=C++, firstline=2, lastline=12, caption=Python example]{main.cpp} : Inclut une portion du code
%
%	\xmakefirstuc : Met en majuscule la 1ere lettre d'un mot
%
% Il est possible de redéfinir les couleurs suivantes :
%
%	primarycolor : Couleur utilisée dans les chapters
%	secondarycolor : Couleur utilisée dans les sections
%	thirdcolor : Couleur utilisée dans les subsections
%	links : Couleur utilisée dans les liens
%	titlecolor : Couleur utilisée dans beamer pour la police des titres
%	titlebg : Couleur utilisée dans beamer pour le fond de la bordure frametitle
%	headbg : Couleur utilisée dans beamer pour le fond de la bordure de section et subsection
%
%	NB : Commande pour changer de couleur -> \definecolor{nomCouleur}{HTML}{FF00FF}
%
% Il est possible de changer le thème pour les codes sources avec la commande suivante :
%
%	\lstset{style=light} ou \lstset{style=dark}
%
% Il est possible de changer une couleur du thème parmis celle ci :
%
%	code<Light/Dark>Background : Couleur utilisée dans le code pour le fond
%	code<Light/Dark>Number : Couleur utilisée dans le code pour chiffres sur le côté
%	code<Light/Dark>Keyword : Couleur utilisée dans le code pour les mots clés principaux
%	code<Light/Dark>Keyword : Couleur utilisée dans le code pour les mots clés secondaires
%	code<Light/Dark>String : Couleur utilisée dans le code pour les chaines de caractère
%	code<Light/Dark>Comment : Couleur utilisée dans le code pour les commentaires
%	code<Light/Dark>Text : Couleur utilisée dans le code pour le reste des caractères
%
% =============================================

% Template :
% [Report / Article]
%
% \documentclass[report]{BetterDocument}
%
% \title{Titre}
% \subtitle{Sous titre}
% \author{L'auteur\\
% 	Un autre auteur
% }
% \date{La date}
% \institute{L'endroit}
%
% \instituteLogo{}
% \docLogo{}
%
%
% \begin{document}
%
% 	\pageDeGarde
%
% 	\tableDesMatieres
%
% \end{document}
%
% =============================================
%
% [Beamer]
% \documentclass[beamer]{BetterDocument}
%
% \title{Titre}
% \author{L'auteur}
% \institute{L'endroit}
% \date{La date}
%
% \begin{document}
%
% 	\frame{\titlepage}
%
% 	\begin{frame}
% 		\frametitle{Table des matières}
% 		\tableofcontents
% 	\end{frame}
%
% 	\section{Avant le BTS}
% 	\subsection{Le collège}
% 	\begin{frame}
% 		\frametitle{Titre de la diapo}
% 		La diapo passionnante
% 	\end{frame}
%
% \end{document}

\NeedsTeXFormat{LaTeX2e}%
\ProvidesClass{BetterDocument}[2019/08/20 BetterDocument, V0.28.1]%

\DeclareOption*{\let\classname\CurrentOption}%
\ProcessOptions\relax%

\@ifundefined{classname}{%
	\newcommand{\classname}{article}%
}{}%

\ifx\classname{beamer}%
	\LoadClass{\classname}%
\else%
	\LoadClass[a4paper, 11pt]{\classname}%
\fi%

\RequirePackage[utf8]{inputenc}%
\RequirePackage[T1]{fontenc}%
\RequirePackage[french]{babel}%
\RequirePackage[labelfont=bf]{caption} % Met en gras les captions
\RequirePackage{fancyhdr} % Permet d'utiliser les headers et footers
\RequirePackage{float} % Permet d'utiliser le modificateur H sur les floattants
\RequirePackage{graphicx} % Permet d'inclure des images
\RequirePackage{ifthen} % Permet de faire des conditions
\RequirePackage{listings} % Permet d'afficher des codes sources
\RequirePackage{mathptmx} % Permet d'utiliser la police Times New Roman
\RequirePackage{mfirstuc} % Permet de mettre en majuscule la première lettre d'un mot
\RequirePackage{multirow} % Permet de fusionner des cellules du tableau
\RequirePackage[section]{placeins} % Permet de bloquer les floats pour ne pas les faire dépasser d'une section par ex
\RequirePackage{setspace} % Permet de changer l'interligne
\RequirePackage{subcaption}
\RequirePackage{tabularx} % Permet d'utiliser les tableaux
\RequirePackage{wrapfig} % Permet d'utiliser les wrapfigure
\RequirePackage[table]{xcolor} % Permet d'utiliser les couleurs
\RequirePackage{longtable}

% Package non compatibles avec Beamer
\ifthenelse{\equal{\classname}{beamer}}{}{%
	\RequirePackage[top=2.5cm, left=2.5cm, right=2.5cm, bottom=2.5cm]{geometry} % Change les bordures des pages
	\RequirePackage[colorlinks]{hyperref} % Supprime les rectangles autour des liens, ajout du \url et \href
	\RequirePackage{titlesec} % Permet de personnaliser les chapters, sections, subsections
}%

\newcommand{\pagestyledefault}[1]{\renewcommand{\pagestyledefault}{\pagestyle{#1}}}%
\ifthenelse{\equal{\classname}{report}}{%
	\pagestyledefault{fancy}%
}{%
	\ifthenelse{\equal{\classname}{article}}{%
		\pagestyledefault{empty}%
	}{%
		\pagestyledefault{plain}%
	}%
}%

% Japonais
\RequirePackage{CJKutf8}%

\newcommand{\japanese}[1]{%
	\begin{CJK}{UTF8}{min}%
		#1%
	\end{CJK}%
}%

% Couleurs
\definecolor{primarycolor}{HTML}{5F445A}%
\definecolor{secondarycolor}{HTML}{57445F}%
\definecolor{thirdcolor}{HTML}{332C3D}%
\definecolor{links}{HTML}{3366BB}%

% Dict
\newcommand{\bdd}{base de données}
\newcommand{\bdds}{bases de données}

\newcommand{\maj}{mise à jour}
\newcommand{\majs}{mises à jour}

\newcommand{\mdp}{mot de passe}
\newcommand{\mdps}{mots de passe}

\newcommand{\os}{système d'exploitation}
\newcommand{\oss}{systèmes d'exploitation}

% Listings
\definecolor{codeDarkBackground}{HTML}{1B1D1E}
\definecolor{codeDarkNumber}{HTML}{888888}
\definecolor{codeDarkKeyword}{HTML}{F92672}
\definecolor{codeDarkKeyword2}{HTML}{66D9EF}
\definecolor{codeDarkString}{HTML}{E6DB74}
\definecolor{codeDarkComment}{HTML}{888888}
\definecolor{codeDarkText}{HTML}{FFFFFF}

\lstdefinestyle{dark}{
	backgroundcolor=\color{codeDarkBackground},
	commentstyle=\color{codeDarkComment},
	keywordstyle=\color{codeDarkKeyword},
	ndkeywordstyle=\color{codeDarkKeyword2},
	numberstyle=\fontsize{8}{10}\selectfont\color{codeDarkNumber},
	stringstyle=\color{codeDarkString},
	basicstyle=\color{codeDarkText}\ttfamily\footnotesize
}

\definecolor{codeLightBackground}{HTML}{FAFAFA}
\definecolor{codeLightNumber}{HTML}{2C91AF}
\definecolor{codeLightKeyword}{HTML}{A626A4}
\definecolor{codeLightKeyword2}{HTML}{E45649}
\definecolor{codeLightString}{HTML}{50A14F}
\definecolor{codeLightComment}{HTML}{A1A2A8}
\definecolor{codeLightText}{HTML}{383A42}

\lstdefinestyle{light}{
	backgroundcolor=\color{codeLightBackground},
	commentstyle=\color{codeLightComment},
	keywordstyle=\color{codeLightKeyword},
	ndkeywordstyle=\color{codeLightKeyword2},
	numberstyle=\fontsize{8}{10}\selectfont\color{codeLightNumber},
	stringstyle=\color{codeLightString},
	basicstyle=\color{codeLightText}\ttfamily\footnotesize
}

\lstset{style=light,
	literate=
	{á}{{\'a}}1 {é}{{\'e}}1 {í}{{\'i}}1 {ó}{{\'o}}1 {ú}{{\'u}}1
	{Á}{{\'A}}1 {É}{{\'E}}1 {Í}{{\'I}}1 {Ó}{{\'O}}1 {Ú}{{\'U}}1
	{à}{{\`a}}1 {è}{{\`e}}1 {ì}{{\`i}}1 {ò}{{\`o}}1 {ù}{{\`u}}1
	{À}{{\`A}}1 {È}{{\'E}}1 {Ì}{{\`I}}1 {Ò}{{\`O}}1 {Ù}{{\`U}}1
	{ä}{{\"a}}1 {ë}{{\"e}}1 {ï}{{\"i}}1 {ö}{{\"o}}1 {ü}{{\"u}}1
	{Ä}{{\"A}}1 {Ë}{{\"E}}1 {Ï}{{\"I}}1 {Ö}{{\"O}}1 {Ü}{{\"U}}1
	{â}{{\^a}}1 {ê}{{\^e}}1 {î}{{\^i}}1 {ô}{{\^o}}1 {û}{{\^u}}1
	{Â}{{\^A}}1 {Ê}{{\^E}}1 {Î}{{\^I}}1 {Ô}{{\^O}}1 {Û}{{\^U}}1
	{Ã}{{\~A}}1 {ã}{{\~a}}1 {Õ}{{\~O}}1 {õ}{{\~o}}1
	{œ}{{\oe}}1 {Œ}{{\OE}}1 {æ}{{\ae}}1 {Æ}{{\AE}}1 {ß}{{\ss}}1
	{ű}{{\H{u}}}1 {Ű}{{\H{U}}}1 {ő}{{\H{o}}}1 {Ő}{{\H{O}}}1
	{ç}{{\c c}}1 {Ç}{{\c C}}1 {ø}{{\o}}1 {å}{{\r a}}1 {Å}{{\r A}}1
	{€}{{\euro}}1 {£}{{\pounds}}1 {«}{{\guillemotleft}}1
	{»}{{\guillemotright}}1 {ñ}{{\~n}}1 {Ñ}{{\~N}}1 {¿}{{?`}}1,
	breakatwhitespace=false,
	breaklines=true,
	captionpos=b,
	keepspaces=true,
	numbers=left,
	numbersep=5pt,
	showspaces=false,
	showstringspaces=false,
	showtabs=false,
	extendedchars=true,
	tabsize=4
}

\lstdefinelanguage{json}{
    morestring=[b]",
    morestring=[d]'
}

% Bullets listes
\frenchsetup{ItemLabeli=\textbullet}
\frenchsetup{ItemLabelii=$\circ$}

% Tabular
\setlength{\arrayrulewidth}{0.2mm}
\setlength{\tabcolsep}{8pt}

% URL et liens
\urlstyle{same}
\hypersetup{
	urlcolor = {links},
	linkcolor = {links},
	citecolor = {links}
}

% Code spécifique à Beamer
\ifthenelse{\equal{\classname}{beamer}}{%
	\setbeamertemplate{headline}{%
		\leavevmode%
		\hbox{%
			\begin{beamercolorbox}[wd=\paperwidth,ht=2.5ex,dp=1.125ex]{section header}%
				\hspace{0.25cm}\insertsection\hfill\insertsubsection\hspace{0.25cm}
			\end{beamercolorbox}%
		}
	}

	\definecolor{titlecolor}{RGB}{249,196,95}
	\definecolor{titlebg}{RGB}{51,51,51}
	\definecolor{headbg}{RGB}{31,31,31}

	\setbeamercolor{frametitle}{fg=titlecolor,bg=titlebg}
	\setbeamercolor{section header}{fg=titlecolor,bg=headbg}
	\setbeamercolor{subsection header}{fg=titlecolor,bg=headbg}

	\setbeamertemplate{background canvas}{
		\includegraphics[height=\paperheight, keepaspectratio]{img/background.jpg}
	}

	\setbeamertemplate{footline}{}

	\setbeamertemplate{navigation symbols}{}
}{
	% Code non compatible avec Beamer
	% Header et footer (seulement pour la classe report)
	\ifthenelse{\equal{\classname}{report}}{%
		\pagestyledefault{}%

		\fancyhf{}%
		\fancyfoot[L] {%
			\thepage{} {\raisebox{-0.6ex}{\color{primarycolor}\fontsize{18}{20}\selectfont\bfseries|}} \title{} - \subtitle{}%
		}%
		\fancyhead[L] {\textbf{\nouppercase\leftmark}}%
		\fancyhead[R] {\nouppercase\rightmark}%

		\fancypagestyle{plain}{%
			\fancyhf{}%
			\renewcommand{\headrulewidth}{0pt}%
			\fancyfoot[L] {%
				\thepage{} {\raisebox{-0.6ex}{\color{primarycolor}\fontsize{18}{20}\selectfont\bfseries|}} \title{} - \subtitle{}%
			}%
		}%

		\titleformat{\chapter}%
		  {\color{primarycolor}\centering\fontsize{20}{24}\selectfont\bfseries}{\thechapter}{0.5em}{}%
		\titlespacing*{\chapter}{0pt}{0pt}{2.3ex plus .2ex}%

		\titleformat*{\section}{\bfseries\color{secondarycolor}\fontsize{16}{20}\selectfont}%
		\titleformat*{\subsection}{\bfseries\color{thirdcolor}\fontsize{14}{17}\selectfont}%
	}{
		\pagestyledefault{}
	}%

	\setlength{\parskip}{0.5em}

	% Numerotation des sections et subsection
	\renewcommand\thesection{\Alph{section}}
	\renewcommand\thesubsection{\roman{subsection}}

	% Commandes page de garde
	\renewcommand{\title}[1]{\renewcommand{\title}{#1}}
	\newcommand{\subtitle}[1]{\renewcommand{\subtitle}{#1}}
	\renewcommand{\author}[1]{\renewcommand{\author}{#1}}
	\newcommand{\institute}[1]{\renewcommand{\institute}{#1}}
	\renewcommand{\date}[1]{\renewcommand{\date}{#1}}

	\newcommand{\instituteLogo}[1]{\renewcommand{\instituteLogo}{#1}}
	\newcommand{\docLogo}[1]{\renewcommand{\docLogo}{#1}}

	\newcommand{\pageDeGarde}{%
		\pagestyle{empty}%
		\pagenumbering{gobble}%

		\ifthenelse{\equal{\instituteLogo}{}}{\null\vfill}{\includegraphics[width=0.35\textwidth, height=0.15\paperheight, keepaspectratio]{\instituteLogo}\vspace{1.5cm}}%
		\vspace{1.5cm}
		\begin{center}%
			\ifthenelse{\equal{\title}{}}{}{{\fontsize{48}{58}\selectfont\title\\}\vspace{1em}}%
			\ifthenelse{\equal{\subtitle}{}}{}{{\fontsize{22}{26}\selectfont\subtitle\\}\vspace{2em}}%
			\ifthenelse{\equal{\docLogo}{}}{}{\includegraphics[width=0.75\textwidth, height=0.25\paperheight, keepaspectratio]{\docLogo}\\\vspace{3em}}%
			\ifthenelse{\equal{\author}{}}{}{\fontsize{15}{18}\selectfont\author\\}%
			\vfill
		\end{center}%
		\ifthenelse{\equal{\date}{}}{}{\date}%
		\ifthenelse{\equal{\institute}{}}{}{\hfill\institute}%

		\newpage%
		\pagenumbering{arabic}%
		\pagestyledefault{}%
	}

	% Commandes table des matières
	\addtocontents{toc}{\setcounter{tocdepth}{2}}
	\AtBeginDocument{\addtocontents{toc}{\protect\thispagestyle{empty}}}
	\newcommand{\tocCounter}[1]{\addtocontents{toc}{\setcounter{tocdepth}{#1}}}

	\newcommand{\tableDesMatieres}{%
		\pagestyle{empty}%
		\pagenumbering{gobble}%

		\begin{spacing}{0.1}%
			\tableofcontents%
		\end{spacing}%

		\newpage%
		\pagenumbering{arabic}%
		\pagestyledefault{}%
	}
}
