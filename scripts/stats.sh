#!/bin/bash

# Constantes
readonly justine="Justine Martin <justine.martin.dev@gmail.com>"
readonly tmp=".tmp"
readonly ignoreTmp=".ignoretmp"

# Prend en paramètre un nom, puis une liste de mails pour calculer le nombres de lignes codées
newPerson() {
	who="$1"
	shift

	# Tant qu'il reste des mails
	while [ ! -z "$1" ]; do
		# Si c'est Justine, on enlève les fichiers autogénérés du premier commit
		if [ "$1" = "$justine" ]; then
			git show de0268fa0bf7d4b1b0adee7d12bf6d441208f3bb --numstat | grep -E "LICENSE|CONVENTIONS\.md|app/build\.gradle|app/gradle/wrapper/gradle-wrapper\.properties|app/gradlew|app/gradlew\.bat|app/settings\.gradle|README\.md" >> "$ignoreTmp"
		fi

		ignoreAdditionsDeletions "$1"
		countAdditionsDeletions "$1"
		shift
	done

	printResults
	clearResults
}

# Ignore, pour un mail donné, les fichiers pour lesquels on ne compte pas les modifications
# Important : Le calcul est grossier car il supprime toutes les modifications qui concernent les livres. Meme si la modification portait sur la restructuration de celui-ci pour changer sa structure
ignoreAdditionsDeletions() {
	git log --numstat --author="$1" | grep -E "doc/.*/BetterDocument\.cls|app/livre" >> "$ignoreTmp"
}

# Compte le nombre de ligne faites
countAdditionsDeletions() {
	git log --numstat  --format="%N" --author="$1" | sed '/^$/d' >> "$tmp"
}

#Affiche le résultat
printResults() {
	# Fait le total des lignes ignorées et l'ajoute au total des lignes codées mais en négatif
	cat "$ignoreTmp" | awk '
		BEGIN {
			a = 0
			d = 0
		}

		{
			a += $1
			d += $2
		}

		END {
			print -a, -d
		}
	' >> "$tmp"

	# Total des lignes codées
	echo -n "$who	"

	# Si ce n'est pas Auréline on ajoute une tabulation pour l'indentation (pas propre mais tant pis)
	if [ ! "$who" = "Auréline" ]; then
		echo -n "	"
	fi

	cat "$tmp" | awk '
		BEGIN {
			a = 0
			d = 0
		}

		{
			a += $1
			d += $2
		}

		END {
			print a "\t\t" d
		}
	'
}

# Supprime les résultats pour une peronne
clearResults() {
	rm "$ignoreTmp"
	rm "$tmp"
}

echo "Auteur		Additions	Suppressions"

newPerson "Auréline" "Auréline <72aureline.derouin@gmail.com>" "Derouin Aureline <21806986@C304L-138C03.campus.unicaen.fr>" "Norah72 <55444884+Norah72@users.noreply.github.com>" "aure <21806986@unicaen.fr>"

newPerson "Dimitri" "Dimitri Stépaniak <dimitri.stepaniak@gmail.com>"

newPerson "Justine" "$justine"

newPerson "Maxime" "Maxime THOMAS <maxim50690@hotamil.com>" "Maxime.T <maxim50690@hotmail.com>"
