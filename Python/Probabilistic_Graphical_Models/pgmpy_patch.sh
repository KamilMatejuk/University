package_dir=$(pip show pgmpy | grep "Location" | awk '{print $2}')

find $package_dir/pgmpy -type f -name "*.py" -exec sed -i 's/np\.int/int/g' {} +

file="$package_dir/pgmpy/factors/discrete/DiscreteFactor.py"
sed -i 's/slice_\[var_index\]\ =\ state/slice_\[var_index\]\ =\ 0\ if\ np\.isnan(state)\ else\ state/g' $file

file="$package_dir/pgmpy/models/BayesianModel.py"
sed -i 's/missing_variables\ =\ set(self.nodes())\ -\ set(data.columns)/missing_variables\ =\ list(set(self.nodes())\ -\ set(data.columns))/g' $file
