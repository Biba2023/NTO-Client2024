package ru.myitschool.work.ui.login;

/*public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private final LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        subscribe();
    }

    private void subscribe() {
        viewModel.getState().clone(getViewLifecycleOwner(), state -> {
            binding.error.setVisibility(View.GONE);
            binding.login.setEnabled(false);
            String numbers = "0123456789";
            binding.username.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = s.toString();
                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(str);
                    boolean b = m.find();
                    if (!str.isEmpty() & str.length() > 3 & numbers.contains(str.substring(0, 1)) & !b){
                        binding.login.setEnabled(true);
                    }
                    else{
                        binding.login.setEnabled(false);
                    }

                }});
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("hasLogIn", Context.MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean("hasLogIn", true);
                e.commit();
                AppCompatActivity activity = (AppCompatActivity) getApplicationContext();
                Fragment fragment = new QrScanFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_login, fragment);
                ft.commit();

            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}*/
