import { IconButton, Tooltip } from "@mui/material";
import RefreshIcon from "@mui/icons-material/Refresh";

type Props = {
  disabled?: boolean;
  tooltip?: string;
  onClick: () => void;
};

export default function RefreshButton({
  disabled = false,
  tooltip = "Refresh",
  onClick,
}: Props) {
  return (
    <Tooltip title={tooltip}>
      <IconButton onClick={onClick} color="primary" disabled={disabled}>
        <RefreshIcon />
      </IconButton>
    </Tooltip>
  );
}
