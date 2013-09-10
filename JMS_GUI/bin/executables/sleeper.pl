#!/bin/perl

my $workdir = ".";

my $inputdir = "$workdir/inputs";
die unless (-d $inputdir);

my $outputdir = "$workdir/outputs";
die unless (-d $outputdir);

my $id = $ARGV[0];
my $t = $ARGV[1];

my $suffix = ".txt";
my $output_filename = "result-${id}.txt";

open(my $OUT, ">$outputdir/$output_filename") or die "Can't open $output_filename for output\n";
print $OUT "This script did nothing but sleep for $t seconds.\n\n\n";
sleep($t);
close($OUT);


