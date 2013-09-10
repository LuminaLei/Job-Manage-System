#!/bin/perl

my $workdir = ".";

my $inputdir = "$workdir/inputs";
die unless (-d $inputdir);

my $outputdir = "$workdir/outputs";
die unless (-d $outputdir);

my $id = $ARGV[0];

my @dataFiles = scanDir("${inputdir}/");
my $suffix = ".txt";
my $output_filename = "result-${id}.txt";

open(my $OUT, ">$outputdir/$output_filename") or die "Can't open $output_filename for output\n";
print $OUT "This script count the number of lines in files.\n\n\n";
foreach $file(@dataFiles){
	open (my $IN, "${inputdir}/${file}") or die "Can't file ${file} in the training data space\n";
	$count = 0;
	while ($_=<$IN>) {
		$count++;
	}
	close ($IN);
	print $OUT "There are $count lines in file ${file}.\n";
}
close($OUT);

### Read files in a dir
### input: dir path end with /
### output: return filenames in an array
sub scanDir{
	my $dir = shift;
	# die "NO such directory! $!" unless(-d $dir);
	# die "Directory cannot open! $!" unless(-r $dir);
	opendir (DIR, $dir) or die "Error dir: $!";
	my @files = readdir(DIR);
	closedir(DIR);
	
	my $i =0;
	local @results;
	foreach my $file (@files)
	{
		if (-f $dir.$file) {
			$results[$i] = $file;
			$i++;
		}
	}
	return @results;
}
