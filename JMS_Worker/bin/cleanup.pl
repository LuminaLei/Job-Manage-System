#!/bin/perl

my $workdir = ".";
my $inputdir = "$workdir/inputs";
die unless (-d $inputdir);

cleanDir("${inputdir}/");

### Delete files in a dir
### input: dir path end with /
sub cleanDir{
	my $dir = shift;
	# die "NO such directory! $!" unless(-d $dir);
	# die "Directory cannot open! $!" unless(-r $dir);
	opendir (DIR, $dir) or die "Error dir: $!";
	my @files = readdir(DIR);
	closedir(DIR);
	
	foreach my $file (@files)
	{
		if (-f $dir.$file) {
			unlink($dir.$file);
		}
	}
}

