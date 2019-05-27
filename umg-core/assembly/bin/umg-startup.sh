#!/usr/bin/env bash
pwd=/data/app/umg/umg/bin
app_name=com.weshare.umg.UMGBootstrap
sh ${pwd}/run_env.sh $app_name 128m 128m $@ 2>&1 >/dev/null &