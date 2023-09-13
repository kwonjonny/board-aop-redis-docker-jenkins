#!/bin/bash
# entrypoint.sh

# 유저와 그룹을 변경하여 실행합니다.
exec gosu ${USER_NAME} "$@"
