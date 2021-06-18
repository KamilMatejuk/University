library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity prescaler_tb is
end prescaler_tb;

-- syswb prescaler.vhd prescaler_tb.vhd --vcd --stop-time=200ns

architecture behavior of prescaler_tb is

    -- UUT (Unit Under Test)
    component prescaler
        generic (
            outNumber : natural
        );
        port (
            regular_clk : in std_logic;
            rst : in std_logic;
            scaled_clk : out std_logic_vector(outNumber - 1 downto 0)
        );
    end component;

    -- set clock period 
    constant regular_clk_period : time := 8 ns; -- 125 MHz
    constant numberOfOutputs : integer := 4;

    -- input signals
    signal regular_clk : std_logic := '0';
    signal rst : std_logic := '0';

    -- input/output signal
    signal scaled_clk : std_logic_vector(numberOfOutputs - 1 downto 0) := (others => '0');

begin
    -- test unit
    uut1 : prescaler
    generic map(
        outNumber => numberOfOutputs
    )
    port map(
        regular_clk => regular_clk,
        rst => rst,
        scaled_clk => scaled_clk
    );

    -- main clock process (125 MHz)
    clk_process : process
    begin
        regular_clk <= '0';
        wait for regular_clk_period/2;
        regular_clk <= '1';
        wait for regular_clk_period/2;
    end process;
end;
