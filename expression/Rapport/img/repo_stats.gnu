set terminal png;
set output 'repo_stats.png';

unset x2tics;
unset y2tics;

set style data histograms;
set style fill solid 1.0 border -1;

plot 'repo_stats.dat' using 2:xtic(1) title 'Ajout' lt rgb "#00B000", \
	 '' using 3:xtic(1) title 'Suppression' lt rgb "#FF0000";
